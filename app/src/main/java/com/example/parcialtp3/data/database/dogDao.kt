package com.example.parcialtp3.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcialtp3.data.entities.Dog
import retrofit2.http.Url

@Dao
interface dogDao {
    @Query("SELECT * FROM dog WHERE location = :location AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsByLocation(location: String): List<Dog>

    @Query("SELECT * FROM dog WHERE breed = :breed AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsByBreed(breed: String): List<Dog>

    @Query("SELECT * FROM dog WHERE breed = :breed AND sub_breed = :subBreed AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsBySubBreed(breed: String, subBreed: String): List<Dog>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createNewDog(dog: Dog?)

    @Query("SELECT * FROM dog")
    fun getAllDogs() : List<Dog>

    @Query("DELETE FROM dog")
    fun deleteAllDogs()

}