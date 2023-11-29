package com.example.parcialtp3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.parcialtp3.R
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PerroActivity : AppCompatActivity() {

    @Inject
    lateinit var dogRepository: DogRepository
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perro)

        var imageDog : ImageView = findViewById(R.id.imageView2)
        var nameDog : TextView = findViewById(R.id.btn_dog_name)
        var locationDog : TextView = findViewById(R.id.location_text)
        var ageDog : TextView = findViewById(R.id.edad_text)
        var sexDog : TextView = findViewById(R.id.sex_details)
        var pesoDog : TextView = findViewById(R.id.sex_details2)
        var nameOwner : TextView = findViewById(R.id.nombre_duenio_text)
        var avatarOwner : ImageView = findViewById(R.id.imageView4)
        var descDog : TextView = findViewById(R.id.textView13)

        val dogId = intent.getIntExtra("dogId", -1)

        if (dogId != -1) {
            val dog = dogRepository.getDogById(dogId)
            val user = userRepository.getUserById(dog.owner_id)

            nameDog.text = dog.name
            locationDog.text = dog.location
            ageDog.text = dog.age.toString()
            sexDog.text = dog.gender
            pesoDog.text = dog.weight.toString()
            nameOwner.text = user.username
            descDog.text = dog.description

            val requestOptions = RequestOptions().transform()
            Glide.with(this)
                .load(dog.dogAvatarUrl)
                .apply(requestOptions)
                .into(imageDog)

            Glide.with(this)
                .load(user.avatar_url)
                .apply(requestOptions)
                .into(avatarOwner)
        }
    }


    }