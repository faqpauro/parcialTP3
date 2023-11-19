package com.example.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.parcialtp3.entities.UserFavorite

@Dao
interface UserFavoriteDao {

    @Insert
    fun registerNewFavorite(userFavorite: UserFavorite)
    @Delete
    fun deleteFavorite(userFavorite: UserFavorite)
    @Query("SELECT * FROM UserFavorite WHERE user_id = :userId AND dog_id = :dogId")
    fun getFavorite(userId: Int, dogId: Int): UserFavorite?
    @Query("SELECT * FROM UserFavorite")
    fun getAllDogs() : List<UserFavorite>
    @Query("SELECT * FROM UserFavorite WHERE user_id = :userId")
    fun getUserFavoritesByUserId(userId: Int): List<UserFavorite>
}