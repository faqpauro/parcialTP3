
package com.example.parcialtp3.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.parcialtp3.R
import com.example.parcialtp3.repository.UserRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val isDarkMode = sharedViewModel.getDarkModeState(this)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<TextView>(R.id.loginButton)


        loginButton.setOnClickListener {
            val usuarioLogin = findViewById<EditText>(R.id.usuarioLogin).text.toString()
            val contraseñaLogin = findViewById<EditText>(R.id.contraseñaLogin).text.toString()

            if (usuarioLogin.isNotEmpty() && contraseñaLogin.isNotEmpty()) {

                lifecycleScope.launch {
                    val userExists = userRepository.checkCredentials(usuarioLogin, contraseñaLogin)
                    if(userExists != null){
                        sharedViewModel.setUserData(this@LoginActivity, userExists)
                        val intent = Intent(this@LoginActivity, MainActivity2::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "No se encontró un usuario con esas credenciales.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(
                    this,
                    "Debes completar todos los campos para continuar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}