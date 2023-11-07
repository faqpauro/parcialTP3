package com.example.parcialtp3.repository

import com.example.parcialtp3.ApiInterface.DogCeoApi
import com.example.parcialtp3.database.AppDatabase
import com.example.parcialtp3.database.dogDao
import com.example.parcialtp3.entities.Dog
import javax.inject.Inject

class DogRepository @Inject constructor(
    val apiService : DogCeoApi,
    private val appDatabase: AppDatabase
) {
    private val dogDao:dogDao = appDatabase.dogDao()
    fun createDogs(){
        dogDao.createNewDog(
            Dog(
                0,
                "Florencia"
                ,"Dingo",
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
                "Ramon"
                ,"Mastiff",
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
                "Leonarda"
                ,"Ovcharka",
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
                "Bobby"
                ,"Boxer",
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
        suspend fun getAllDogs() : List<Dog> {
            return dogDao.getAllDogs()
        }

     fun clearAllData(){
        return dogDao.deleteAllDogs()
    }


}