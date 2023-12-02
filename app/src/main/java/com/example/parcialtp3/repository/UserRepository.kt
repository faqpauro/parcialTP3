package com.example.parcialtp3.repository

import com.example.parcialtp3.database.AppDatabase
import com.example.parcialtp3.database.userDao
import com.example.parcialtp3.entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {
    private val userDao: userDao = appDatabase.userDao()
    suspend fun checkCredentials(username: String, password: String):User? {
        val usuarioExiste = userDao.getUserByCredentials(username, password)
        return usuarioExiste
    }

    suspend fun registerNewUser(user: User): Boolean {
        val cargado = userDao.insertUser(user)
        return cargado > 0 // me devuelve el id o 0
    }

    suspend fun updateUserImage(avatarUrl: String, id: Int?): Boolean {
        val actualizado = userDao.updateUserAvatar(avatarUrl, id)
        return actualizado > 0 // si es mayor a 0, actualizó
    }

    suspend fun updateUserDarkMode(darkMode: Boolean, id: Int?) {
        if (id != null) {
            userDao.updateUserDarkModeSelection(darkMode, id)
        }
    }

    fun getUserById(ownerId: Int): User {
        return userDao.getUserById(ownerId)
    }

}