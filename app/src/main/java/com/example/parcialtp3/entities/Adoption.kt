package com.example.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Adoption(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id: Int,
    @ColumnInfo(name="owner_id") val ownerId: Int,
    @ColumnInfo(name="dog_id") val dogId: Int,
    @ColumnInfo(name="date") val date: Date

)