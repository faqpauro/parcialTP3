package com.example.parcialtp3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.parcialtp3.entities.Adoption
import com.example.parcialtp3.entities.DateConverter
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.entities.UserFavorite
import com.example.parcialtp3.database.userDao
import com.example.parcialtp3.database.dogDao
import com.example.parcialtp3.database.adoptionDao

@Database(entities = [User::class, Dog::class, Adoption::class, UserFavorite::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): userDao
    abstract fun dogDao(): dogDao
    abstract fun adoptionDao(): adoptionDao

    fun clearAllData() {
        runInTransaction {
            dogDao().deleteAllDogs()
        }
    }
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

        /*private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aquí puedes escribir las instrucciones SQL necesarias para modificar la tabla "Dog"
                // para que sea compatible con la nueva versión 3 de la base de datos.
                database.execSQL("ALTER TABLE Dog ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
            }
        }*/
    }
}