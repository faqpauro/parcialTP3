package com.example.parcialtp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialtp3.ApiInterface.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var URL_BASE_API : String = "https://dog.ceo/api/breed/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getRetrofit(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(URL_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    private fun buscarRaza(query:String){
        CoroutineScope(Dispatchers.IO).launch{
            val call = getRetrofit().getDogsByBreeds("$query/images")
            val dogs = call.body()
            if (call.isSuccessful) {
                // mostrar
            }else{
                // mostrar error
            }
        }
    }
}