package com.example.parcialtp3.ApiInterface

import com.example.parcialtp3.DataModels.DogBreedsAndSubBreedsResponse
import com.example.parcialtp3.DataModels.DogResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogCeoApi {
    @GET("breed/{breed}/{subBreed}/images/random")
    suspend fun getDogImageByBreed(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Response<DogResponse>
    @GET("breed/{breed}/images/random")
    suspend fun getDogImageByBreed(@Path("breed") breed: String): Response<DogResponse>
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<DogResponse>
    @GET("breeds/list/all")
    suspend fun getDogBreedsAndSubBreeds(): Response<DogBreedsAndSubBreedsResponse>
}