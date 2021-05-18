package com.andromega.interviewmvpproject.interfaces

import com.andromega.interviewmvpproject.modelclass.PlaceSearchModelClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v2/venues/search?")

    fun getNearPlaces(@Query("ll") ll: String,
                      @Query("client_id") clientId: String,
                      @Query("client_secret") clientSecret: String,
                      @Query("v")  v: String,
                      @Query("query") queryWord: String,
                      @Query("limit") limit: String,
                      ): Call<PlaceSearchModelClass>

    companion object{

        fun create(): SearchApi{
        val retrofitSearch = Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofitSearch.create(SearchApi::class.java)
        }
    }

}