package com.example.parcialtp3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcialtp3.entities.User

@Dao
interface userDao {

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUserByCredentials(username: String, password: String): User?

    @Query("SELECT dark_mode_selection FROM user WHERE id = :id")
    fun getUserHasDarkModeTurnedOn(id: Int): Boolean?

    @Query("UPDATE user SET dark_mode_selection = :newValue WHERE id = :id")
    fun updateUserDarkModeSelection(newValue: Boolean, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}