package com.example.parcialtp3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "avatar_url") val avatar_url : String,
    @ColumnInfo(name = "dark_mode_selection") val darkModeSelection : Boolean
)