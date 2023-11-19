package com.example.parcialtp3.DataModels

import com.google.gson.annotations.SerializedName

data class DogBreedsAndSubBreedsResponse(

    @SerializedName("message")
    val breedsAndSubBreeds: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)
