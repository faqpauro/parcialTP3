package com.example.parcialtp3.DogModel

import com.google.gson.annotations.SerializedName

data class Dog (
    @SerializedName("message") var images: List<String>,
    @SerializedName("status") var status: String
)