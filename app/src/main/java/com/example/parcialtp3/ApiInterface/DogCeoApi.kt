package com.example.parcialtp3.ApiInterface

import com.example.parcialtp3.DataModels.DogResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DogCeoApi {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogResponse>
}