package com.example.parcialtp3.repository

import com.example.parcialtp3.ApiInterface.DogCeoApi
import com.example.parcialtp3.entities.Filter
import com.example.parcialtp3.database.AppDatabase
import com.example.parcialtp3.database.dogDao
import com.example.parcialtp3.entities.Dog
import javax.inject.Inject

class DogRepository @Inject constructor(
    val apiService : DogCeoApi,
    private val appDatabase: AppDatabase
) {
    private val dogDao: dogDao = appDatabase.dogDao()

    private val breedList: MutableList<Filter> = mutableListOf()

    fun createNewDog( name : String, breed : String, subBreed : String,
                      gender : String, weight : Double, location : String,
                      description : String, owner_id : Int, age : Int) : Boolean {

        val cargado = dogDao.createNewDog(
            Dog(
                0,
                name,
                breed,
                subBreed,
                "https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg",
                gender,
                weight,
                location,
                description,
                owner_id,
                age
            )
        )
        return cargado > 0
    }
    fun createDogs(){

        dogDao.createNewDog(
            Dog(
                0,
                "Florencia", "Dingo",
                "",
                "https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg",
                "Femenino",
                10.0,
                "Buenos Aires",
                "Raza : Dingo",
                1,
                5
            )
        )

        dogDao.createNewDog(
            Dog(
                0,
                "Ramon", "Mastiff",
                "Tibetan",
                "https://images.dog.ceo/breeds/mastiff-tibetan/n02108551_660.jpg",
                "Maculino",
                7.0,
                "Formosa",
                "Raza : Mastiff - Tibetan",
                2,
                5
            )
        )

        dogDao.createNewDog(
            Dog(
                0,
                "Leonarda", "Ovcharka",
                "Caucasian",
                "https://images.dog.ceo/breeds/ovcharka-caucasian/IMG_20190826_112025.jpg",
                "Femenino",
                6.5,
                "Chubut",
                "Raza : Ovcharka - Caucasian",
                3,
                7
            )
        )

        dogDao.createNewDog(
            Dog(
                0,
                "Bobby", "Boxer",
                "",
                "https://images.dog.ceo/breeds/boxer/n02108089_6295.jpg",
                "Femenino",
                6.5,
                "Chubut",
                "Raza : Boxer",
                4,
                9
            )
        )
    }

    fun getAllDogs(): List<Dog> {
        return dogDao.getAllDogs()
    }

    fun clearAllData() {
        return dogDao.deleteAllDogs()
    }

    fun getBreedList(): List<Filter> {
        breedList.clear()
        val dogList = getAllDogs()
        for (dog in dogList) {
            val breed = dog.breed
            if (breed.isNotEmpty() && !breedList.contains(Filter(breed))) {
                breedList.add(Filter(breed))
            }
            val subBreed = dog.subBreed
            if (subBreed.isNotEmpty() && !breedList.contains(Filter(subBreed))) {
                breedList.add(Filter(subBreed))
            }
        }
        return breedList
    }
}


