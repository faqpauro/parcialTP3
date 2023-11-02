package com.example.parcialtp3.ApiInterface

import com.example.parcialtp3.DogModel.Dog
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<Dog>

    suspend fun getBreeds(@Url url:String):Response

}