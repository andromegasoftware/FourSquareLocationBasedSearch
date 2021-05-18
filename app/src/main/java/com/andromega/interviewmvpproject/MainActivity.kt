package com.andromega.interviewmvpproject

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andromega.interviewmvpproject.adapter.SearchAdapter
import com.andromega.interviewmvpproject.interfaces.PlaceContracts
import com.andromega.interviewmvpproject.interfaces.SearchViewPresenter
import com.andromega.interviewmvpproject.modelclass.PlaceSearchModelClass
import com.andromega.interviewmvpproject.modelclass.Venue
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchViewPresenter, PlaceContracts.View {

    private lateinit var searchAdapter: SearchAdapter
    lateinit var searchList: List<Venue>
    private lateinit var presenter : SearchPresenter

    var queryWord: String = ""

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    var locationLatitude: Double = 0.0
    var locationLongitude: Double = 0.0
    var ll: String = ""
    lateinit var clientId: String
    lateinit var clientSecret: String

    private var PERMISSION_ID = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientId = resources.getString(R.string.client_id)
        clientSecret = resources.getString(R.string.client_secret)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
        placeSearch()

        presenter = SearchPresenter(this)

    }

    override fun onDataCompleteFromApi(search: List<Venue>) {

        searchList = search

    }

    override fun onDataErrorFromApi(throwable: Throwable) {

    }

    private fun placeSearch() {
        searchList = emptyList()
        searchAdapter = SearchAdapter(searchList) {}


        // movies search results recyclerview
        val searchLayoutManager = LinearLayoutManager(this)
        searchLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = searchLayoutManager
        recyclerView.adapter = searchAdapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.isActivated = true
        searchView.isSelected = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == null || p0 == "")
                    return false

                queryWord = p0
                presenter.requestDataFromServer(object : PlaceContracts.Model.OnFinishedListener {
                    override fun onFinished(search: List<Venue>) {
                        searchAdapter.submitList(search)
                        searchAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(t: Throwable?) {

                    }
                }, ll, queryWord, clientId, clientSecret)


                return false
            }
        })
    }

    //get the location
    @SuppressLint("MissingPermission")
    fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        //if location is null, get the new user location
                        getNewLocation()
                    } else {
                        locationLatitude = location.latitude
                        locationLongitude = location.longitude
                        ll = "$locationLatitude,$locationLongitude"

                        Log.d("location:", ll)
                    }
                }
            } else {
                Toast.makeText(this, "Please Enable Device Location Service", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            requestPermission()
        }
    }

    //get new location
    @SuppressLint("MissingPermission")
    fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()

        )
    }

    //create locationCallback variable
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation: Location = p0.lastLocation

            // set the new location
            locationLatitude = lastLocation.latitude
            locationLongitude = lastLocation.longitude

            Log.d("location:", "$locationLatitude,$locationLongitude")
        }
    }

    // check the permission
    private fun checkPermission(): Boolean {

        if (
            ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {

            return true
        }

        return false
    }

    // get the permission
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    //check if the location service of device is enabled
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    //check the permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("check_permission: ", "we have the permission")
                recreate()
            }
        }
    }

    override fun setDataToRecyclerView(placeArrayList: List<PlaceSearchModelClass>) {

    }

    override fun onResponseFailure(throwable: Throwable?) {

    }
}