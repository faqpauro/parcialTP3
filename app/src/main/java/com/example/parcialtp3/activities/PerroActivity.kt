package com.example.parcialtp3.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.parcialtp3.R
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.repository.UserRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PerroActivity : AppCompatActivity() {

    @Inject
    lateinit var dogRepository: DogRepository
    @Inject
    lateinit var userRepository: UserRepository
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        setAppTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perro)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "Detalles"
            it.setDisplayHomeAsUpEnabled(true)
        }

        var imageDog : ImageView = findViewById(R.id.imageView2)
        var nameDog : TextView = findViewById(R.id.btn_dog_name)
        var locationDog : TextView = findViewById(R.id.location_text)
        var ageDog : TextView = findViewById(R.id.edad_text)
        var sexDog : TextView = findViewById(R.id.sex_details)
        var pesoDog : TextView = findViewById(R.id.sex_details2)
        var nameOwner : TextView = findViewById(R.id.nombre_duenio_text)
        var avatarOwner : ImageView = findViewById(R.id.imageView4)
        var descDog : TextView = findViewById(R.id.textView13)
        var botonTelefono = findViewById<ImageView>(R.id.imageView5)

        val dogId = intent.getIntExtra("dogId", -1)

        if (dogId != -1) {
            val dog = dogRepository.getDogById(dogId)
            val user = userRepository.getUserById(dog.owner_id)

            nameDog.text = dog.name
            locationDog.text = dog.location
            ageDog.text = if (dog.age == 1) "${dog.age} año" else "${dog.age} años"
            sexDog.text = "Sexo: " + dog.gender
            pesoDog.text = "Peso: " + dog.weight.toString() + " kg"
            nameOwner.text = user.username
            descDog.text = "Descripción: " + dog.description

            val requestOptions = RequestOptions().transform()
            Glide.with(this)
                .load(dog.dogAvatarUrl)
                .apply(requestOptions)
                .into(imageDog)

            Glide.with(this)
                .load(user.avatar_url)
                .apply(requestOptions)
                .into(avatarOwner)

            botonTelefono.setOnClickListener{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + user.phone)
                startActivity(intent)
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setAppTheme() {
        val isDarkMode = sharedViewModel.getUserData(this)?.darkModeSelection ?: false
        if (isDarkMode) {
            setTheme(R.style.Theme_ParcialTP3_Dark)
        } else {
            setTheme(R.style.Theme_ParcialTP3)
        }
    }



    }