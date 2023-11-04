package com.example.parcialtp3.data.entities

import androidx.room.TypeConverter
import java.util.Date

/**
 * Conversor de fechas. SQLite no soporta Date según se investigó, entonces convertimos
 * la fecha A y DESDE timestamp para trabajar con ella.
 */
class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }
    @TypeConverter
    fun toDate(timestamp: Long?): Date?{
        return timestamp?.let { Date(it) }
    }
}