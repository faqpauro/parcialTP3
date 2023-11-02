package com.example.parcialtp3.ApiInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogRetrofit {

    private val URL_BASE_API  = "https://dog.ceo/api/breed/"

    private fun getRetrofit(): DogCeoApi {
        return Retrofit.Builder()
            .baseUrl(URL_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogCeoApi::class.java)
    }
    
}