package com.chama.googleplacestest.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitGooglePlacesInstance {

    private fun initRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val service: IPlacesService = initRetrofit()
        .create(IPlacesService::class.java)
}