package com.example.parcialtp3.viewmodels

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parcialtp3.entities.User
import com.google.gson.Gson

class SharedViewModel : ViewModel() {
    private val _darkMode = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()

    fun saveDarkModeState(context: Context, isDarkMode: Boolean) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("darkMode", isDarkMode)
        editor.apply()
    }

    fun getDarkModeState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("darkMode", false)
    }

    fun setUserData(context: Context, user: User){
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user", Gson().toJson(user))
        editor.apply()
    }

    fun getUserData(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", null)

        if (user.isNullOrEmpty()){
            return null
        }

        return Gson().fromJson(user, User::class.java)
    }
}