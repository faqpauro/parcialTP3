package com.example.parcialtp3.database

import androidx.room.Dao
import androidx.room.Query
import com.example.parcialtp3.entities.Dog

@Dao
interface dogDao {
    @Query("SELECT * FROM dog WHERE location = :location AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsByLocation(location: String): List<Dog>

    @Query("SELECT * FROM dog WHERE breed = :breed AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsByBreed(breed: String): List<Dog>

    @Query("SELECT * FROM dog WHERE breed = :breed AND sub_breed = :subBreed AND id NOT IN (SELECT dog_id FROM Adoption)")
    fun getDogsBySubBreed(breed: String, subBreed: String): List<Dog>

}