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

@Database(entities = [User::class, Dog::class, Adoption::class, UserFavorite::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class appDatabase : RoomDatabase() {

    abstract fun userDao() : userDao
    abstract fun dogDao() : dogDao
    abstract fun adoptionDao() : adoptionDao

    companion object {
        var INSTANCE: appDatabase? = null
        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDatabase::class.java,
                        "adoptionDb"
                    ).addMigrations().allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }


    }

}