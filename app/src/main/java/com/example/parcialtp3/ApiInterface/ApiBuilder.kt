package com.example.parcialtp3.ApiInterface

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiBuilder {

    private val URL_BASE_API  = "https://dog.ceo/api/breeds/"

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun providesDogApiClient(retrofit: Retrofit): DogCeoApi {
        return retrofit.create(DogCeoApi::class.java)
    }

}