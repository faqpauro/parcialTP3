package com.example.parcialtp3.activities

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.parcialtp3.R
import com.example.parcialtp3.repository.UserRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ConfiguracionActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var darkModeSwitch: Switch
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setAppTheme()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_configuracion)
        initializeUI()
    }

    private fun initializeUI() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "Configuración"
            it.setDisplayHomeAsUpEnabled(true)
        }
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        val isDarkMode = sharedViewModel.getDarkModeState(this)
        darkModeSwitch.isChecked = isDarkMode

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != isDarkMode) {
                sharedViewModel.saveDarkModeState(this, isChecked)
                lifecycleScope.launch {
                    userRepository.updateUserDarkMode(isChecked, sharedViewModel.getUserData(this@ConfiguracionActivity)?.id)

                    recreate() // Recrea la Activity solo si el estado ha cambiado
                }
            }
        }
    }

    private fun setAppTheme() {
        val isDarkMode = sharedViewModel.getDarkModeState(this)
        setTheme(if (isDarkMode) R.style.Base_Theme_ParcialTP3_Dark else R.style.Base_Theme_ParcialTP3)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Esto hace que la actividad responda igual que si se presionara el botón de atrás
        return true
    }

    override fun onStart() {
        setAppTheme()
        super.onStart()
    }
}