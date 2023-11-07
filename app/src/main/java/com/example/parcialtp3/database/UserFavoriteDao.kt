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

}