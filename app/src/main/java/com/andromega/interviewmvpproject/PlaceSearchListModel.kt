package com.andromega.interviewmvpproject

import android.util.Log
import com.andromega.interviewmvpproject.interfaces.PlaceContracts
import com.andromega.interviewmvpproject.interfaces.SearchApi
import com.andromega.interviewmvpproject.modelclass.PlaceSearchModelClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchListModel : PlaceContracts.Model{
    private val TAG = "PlaceSearchListModel"
    override fun getPlaceSearchModelClassList(
        finishListener: PlaceContracts.Model.OnFinishedListener?,
        ll: String,
        queryWord: String,
        clientId: String,
        clientSecret: String
    ) {
        SearchApi.create()
            .getNearPlaces(ll,  clientId, clientSecret, "20210515", queryWord, "20")
            .enqueue(object : Callback<PlaceSearchModelClass> {

                override fun onResponse(
                    call: Call<PlaceSearchModelClass>, response: Response<PlaceSearchModelClass>
                ) {
                    val responseBody = response.body()
                    responseBody?.let { body ->
                        val venues = body.response.venues
                        finishListener?.onFinished(venues)
                        Log.d(TAG, "onResponse: $venues")
                    } ?: Log.e(TAG, "responseBody == null or something missing")

                }

                override fun onFailure(call: Call<PlaceSearchModelClass>, t: Throwable) {
                    finishListener?.onFailure(t)
                }
            })
    }


}