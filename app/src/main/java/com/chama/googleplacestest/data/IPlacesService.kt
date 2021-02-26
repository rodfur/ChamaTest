package com.chama.googleplacestest.data

import com.chama.googleplacestest.data.model.NearbySearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IPlacesService {

    // example: https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY

    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String = "0.0,0.0",
        @Query("radius") radius: Int = 1500,
        @Query("type") type: String = "restaurant,cafe,bar",
        @Query("key") key: String = ""
    ): Call<NearbySearchResponse>
}