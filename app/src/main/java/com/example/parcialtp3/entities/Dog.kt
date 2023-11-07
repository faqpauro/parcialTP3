package com.example.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dog")
data class Dog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "breed") val breed: String,
    @ColumnInfo(name = "sub_breed") val subBreed: String,
    @ColumnInfo(name = "dog_avatar_url") val dogAvatarUrl: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "owner_id") val owner_id: Int,
    @ColumnInfo(name = "age") val age: Int
)