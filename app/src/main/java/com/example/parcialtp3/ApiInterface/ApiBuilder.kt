package com.example.parcialtp3.ApiInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiBuilder {

    private val URL_BASE_API  = "https://dog.ceo/api/breed/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): DogCeoApi {
        return retrofit.create(DogCeoApi::class.java)
    }

}