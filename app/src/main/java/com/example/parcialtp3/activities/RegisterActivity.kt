package com.example.parcialtp3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.parcialtp3.R
import com.example.parcialtp3.database.appDatabase
import com.example.parcialtp3.database.userDao
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.viewmodels.SharedViewModel

class RegisterActivity : AppCompatActivity() {

    private var db: appDatabase? = null
    private var userDao: userDao? = null

    lateinit var registerButton: Button
    lateinit var userInput: TextView
    lateinit var phoneInput: TextView
    lateinit var passwordInput: TextView
    lateinit var emailInput: TextView
    lateinit var avatar: TextView

    lateinit var darkModeInput: Switch

    var i : Int = 0;

    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.registerButton)
        userInput = findViewById(R.id.usuarioLogin)
        passwordInput = findViewById(R.id.contraseñaLogin)
        phoneInput = findViewById(R.id.telefonoLogin)
        emailInput = findViewById(R.id.correoLogin)
        darkModeInput = findViewById(R.id.darkMSwitchRegister)
        avatar = findViewById(R.id.avatar)

        darkModeInput.isChecked = sharedViewModel.getDarkModeState(this)
        updateDarkModeState(darkModeInput.isChecked)
    }

    private fun updateDarkModeState(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    override fun onStart() {
        super.onStart()
        darkModeInput.setOnCheckedChangeListener {_, isChecked ->
            sharedViewModel.saveDarkModeState(this, isChecked)
            updateDarkModeState(isChecked)
        }
        db=appDatabase.getAppDataBase(this)
        userDao = db?.userDao()
        val username: String = userInput.text.toString()
        val contraseña: String = passwordInput.text.toString()
        val telefono: String = phoneInput.text.toString()
        val email: String = emailInput.text.toString()
        val avatar: String = avatar.text.toString()

        registerButton.setOnClickListener{
            //cargar mas informacion de usuario
            userDao?.insertUser(User(i, username,contraseña,email,avatar,darkModeInput.isChecked))
        }
    }



}