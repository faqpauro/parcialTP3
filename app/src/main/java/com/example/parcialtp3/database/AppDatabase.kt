package com.example.parcialtp3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.parcialtp3.entities.Adoption
import com.example.parcialtp3.entities.DateConverter
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.entities.UserFavorite

@Database(entities = [User::class, Dog::class, Adoption::class, UserFavorite::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): userDao
    abstract fun dogDao(): dogDao
    abstract fun adoptionDao(): adoptionDao
    abstract fun UserFavoriteDao() : UserFavoriteDao

    companion object {
        var INSTANCE: AppDatabase? = null
        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "adoptionDb"
                    ).addMigrations()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }

        fun destroyDatabase() {

            INSTANCE = null
        }

    }
}