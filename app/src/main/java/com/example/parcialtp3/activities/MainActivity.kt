package com.example.parcialtp3.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.example.parcialtp3.ApiInterface.ApiBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.parcialtp3.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnGetStarted = findViewById<Button>(R.id.btn_get_started)

        btnGetStarted.setOnClickListener(){
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }
    }


/*
    private fun buscarRaza(query:String){
        val service = ApiBuilder.create()
        CoroutineScope(Dispatchers.IO).launch{
            val call = service.getDogsByBreeds("$query/images")
            val dogs = call.body()
            if (call.isSuccessful) {
                val images = dogs?.images ?: emptyList()
                Log.i("perros", images.toString())
            }else{
                Log.e("ERROR_YO", "tiró un error")
            }
        }
    }

 */
}