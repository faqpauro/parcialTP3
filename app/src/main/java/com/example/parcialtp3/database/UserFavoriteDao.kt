package com.example.parcialtp3.database

import androidx.room.Dao
import androidx.room.Query
import com.example.parcialtp3.entities.UserFavorite

@Dao
interface UserFavoriteDao {

    @Query("INSERT INTO UserFavorite (user_id, dog_id) VALUES (:userId, :dogId)")
    fun registerNewFavorite(userId: Int, dogId: Int)

    @Query("DELETE FROM UserFavorite WHERE user_id = :userId AND dog_id = :dogId")
    fun deleteFavorite(userId: Int, dogId: Int)

}