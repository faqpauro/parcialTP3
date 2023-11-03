package com.example.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
internal class Dog {
    @ColumnInfo(name = "id")
    var id: `val`? = null
}