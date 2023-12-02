package com.example.parcialtp3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialtp3.R
import com.example.parcialtp3.viewmodels.SharedViewModel


class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        val btnGetStarted = findViewById<Button>(R.id.btn_get_started)
        btnGetStarted.setOnClickListener(){
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
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