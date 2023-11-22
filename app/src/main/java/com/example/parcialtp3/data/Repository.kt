package com.example.parcialtp3.data

import com.example.parcialtp3.database.dogDao
import com.example.parcialtp3.database.userDao
import javax.inject.Inject


class Repository @Inject constructor(

    private val userDao: userDao,
    private val dogDao: dogDao

) {

    suspend fun updateUserDarkModeSelection(value: Boolean, id: Int){
        userDao.updateUserDarkModeSelection(value, id)
    }

}