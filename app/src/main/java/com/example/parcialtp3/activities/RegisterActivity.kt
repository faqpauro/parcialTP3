package com.example.parcialtp3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.parcialtp3.R
import com.example.parcialtp3.database.AppDatabase
import com.example.parcialtp3.database.userDao
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.repository.UserRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var userRepository: UserRepository

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


        registerButton.setOnClickListener{

            val username: String = userInput.text.toString()
            val contraseña: String = passwordInput.text.toString()
            val telefono: String = phoneInput.text.toString()
            val email: String = emailInput.text.toString()
            val avatar: String = avatar.text.toString()
            val darkMode: Boolean = darkModeInput.isChecked

            if (username.isEmpty() || contraseña.isEmpty() || telefono.isEmpty() || email.isEmpty() || avatar.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Todos los campos deben estar completados", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    if(userRepository.registerNewUser(User(0, username, contraseña, email, avatar, darkMode, telefono))){
                        // userRepository.registerNewUser(User(0, username, contraseña, email, telefono , avatar, darkMode))
                        Toast.makeText(this@RegisterActivity, "Usuario cargado con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegisterActivity, "Ocurrió un error interno al intentar cargar un usuario.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }



}