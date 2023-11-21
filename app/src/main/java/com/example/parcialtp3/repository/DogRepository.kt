package com.example.parcialtp3.repository

import com.example.parcialtp3.ApiInterface.DogCeoApi
import com.example.parcialtp3.database.AppDatabase
import com.example.parcialtp3.database.UserFavoriteDao
import com.example.parcialtp3.database.dogDao
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.entities.Filter
import com.example.parcialtp3.entities.UserFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import kotlin.math.absoluteValue

class DogRepository @Inject constructor(
    val apiService : DogCeoApi,
    private val appDatabase: AppDatabase
) {
    private val dogDao: dogDao = appDatabase.dogDao()
    private val userFavoriteDao: UserFavoriteDao = appDatabase.UserFavoriteDao()

    private val breedList: MutableList<Filter> = mutableListOf()


    suspend fun createNewDog( name : String, breed : String, subBreed : String,
                      gender : String, weight : Double, location : String,
                      description : String, owner_id : Int, age : Int) : Boolean {

        var cargado : Long = -1
        val existingDog = dogDao.getAllDogs().find {
            it.name == name &&
                    it.breed == breed &&
                    it.subBreed == subBreed &&
                    it.gender == gender &&
                    it.weight == weight &&
                    it.location == location &&
                    it.description == description &&
                    it.owner_id == owner_id &&
                    it.age == age
        }

        if(existingDog == null){
            val imageUrl = getDogImageUrl(breed, subBreed)
            cargado = dogDao.createNewDog(
                Dog(
                    0,
                    name,
                    breed,
                    subBreed,
                    imageUrl,
                    gender,
                    weight,
                    location,
                    description,
                    owner_id,
                    age
                )
            )
        }


        return cargado > 0
    }
    fun createDogs(){
        val dog1 = Dog(0, "Florencia", "Dingo", "", "https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg", "Femenino", 10.0, "Buenos Aires", "Raza : Dingo", 1, 5)
        val dog2 = Dog(0, "Ramon", "Mastiff", "Tibetan", "https://images.dog.ceo/breeds/mastiff-tibetan/n02108551_660.jpg", "Masculino", 7.0, "Formosa", "Raza : Mastiff - Tibetan", 2, 5)
        val dog3 = Dog(0, "Leonarda", "Ovcharka", "Caucasian", "https://images.dog.ceo/breeds/ovcharka-caucasian/IMG_20190826_112025.jpg", "Femenino", 6.5, "Chubut", "Raza : Ovcharka - Caucasian", 3, 7)
        val dog4 = Dog(0, "Bobby", "Boxer", "", "https://images.dog.ceo/breeds/boxer/n02108089_6295.jpg", "Femenino", 6.5, "Chubut", "Raza : Boxer", 4, 9)

        val existingDog = findExistingDog(dog1)
        val existingDog2 = findExistingDog(dog2)
        val existingDog3 = findExistingDog(dog3)
        val existingDog4 = findExistingDog(dog4)

        if(existingDog == null || existingDog2 == null || existingDog3 == null || existingDog4 == null){
            dogDao.createNewDog(dog1)
            dogDao.createNewDog(dog2)
            dogDao.createNewDog(dog3)
            dogDao.createNewDog(dog4)
        }

    }
    private fun findExistingDog(newDog: Dog): Dog? {
        var listaDog = dogDao.getAllDogs()

        return dogDao.getAllDogs().find {
            it.name == newDog.name &&
                    it.breed == newDog.breed &&
                    it.subBreed == newDog.subBreed &&
                    it.gender == newDog.gender &&
                    isApproximatelyEqual(it.weight, newDog.weight) &&
                    it.location == newDog.location &&
                    it.description == newDog.description &&
                    it.owner_id == newDog.owner_id &&
                    it.age == newDog.age
        }
    }

    private fun isApproximatelyEqual(value1: Double, value2: Double, tolerance: Double = 0.0001): Boolean {
        return (value1 - value2).absoluteValue < tolerance
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

    fun getFavoriteDogs(userId: Int): List<Dog> {
        val favoriteDogs = mutableListOf<Dog>()
        val userFavorites = userFavoriteDao.getUserFavoritesByUserId(userId)
        for (userFavorite in userFavorites) {
            val dog = dogDao.getDogById(userFavorite.dogId)
            if (dog != null) {
                favoriteDogs.add(dog)
            }
        }
        return favoriteDogs
    }

    fun getUserFavorite(dogId: Int, userId: Int): UserFavorite? {
        return userFavoriteDao.getFavorite(userId, dogId)
    }

    fun getDogById(dogId: Int): Dog {
        return dogDao.getDogById(dogId)
    }

    fun isFavorite(userId: Int, dogId: Int): Boolean {
        return getFavoriteDogs(userId).any { it?.id == dogId }
    }

    fun deleteFavorite(userFavorite: UserFavorite) {
        userFavoriteDao.deleteFavorite(userFavorite)
    }

    fun createNewFavorite(userFavorite: UserFavorite) {
        userFavoriteDao.registerNewFavorite(userFavorite)
    }

    suspend fun getDogBreedsAndSubBreeds() : Map<String, List<String>>{
        try{
            val response = apiService.getDogBreedsAndSubBreeds()
            if(response.isSuccessful){
                val breedsAndSubBreedsResponse = response.body()
                return breedsAndSubBreedsResponse?.breedsAndSubBreeds ?: emptyMap()
            }
        }catch (e: Exception){ }
        return emptyMap()
    }

    private suspend fun getDogImageUrl(breed: String, subBreed: String): String {
        return try {
            val response = if (subBreed.isNotEmpty()) {
                apiService.getDogImageByBreed(breed, subBreed)
            } else {
                apiService.getDogImageByBreed(breed)
            }

            if (!response.isSuccessful || response.body()?.status != "success") {
                return getFallbackImageUrl()
            }

            val imageUrl = response.body()?.message.orEmpty()
            if (isValidImageUrl(imageUrl)) return imageUrl

            getFallbackImageUrl()
        } catch (e: Exception) {
            getFallbackImageUrl()
        }
    }

    private suspend fun getFallbackImageUrl(): String {
        val response = apiService.getRandomDogImage()
        return if (response.isSuccessful && response.body()?.status == "success" && isValidImageUrl(response.body()?.message.orEmpty())) {
            response.body()?.message.orEmpty()
        } else {
            "https://images.dog.ceo/breeds/bluetick/n02088632_3230.jpg" //imagen por defecto por si no encuentra imagen o da error
        }
    }

    private suspend fun isValidImageUrl(url: String): Boolean {
        return withContext(Dispatchers.IO) { // Ejecuta en el hilo de I/O
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).head().build()
                client.newCall(request).execute().use { response ->
                    response.isSuccessful && isImageContentType(response)
                }
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun isImageContentType(response: okhttp3.Response): Boolean {
        val contentType = response.header("Content-Type")
        return contentType != null && contentType.startsWith("image/")
    }

}


