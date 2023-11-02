package com.example.parcialtp3.activities

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.parcialtp3.R


class ConfiguracionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setTitle("Configuración")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }


        val darkModeSwitch: Switch = findViewById(R.id.darkModeSwitch)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveDarkModeState(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveDarkModeState(false)
            }
        }

        darkModeSwitch.isChecked = loadDarkModeState()
    }

    private fun saveDarkModeState(isDarkMode: Boolean) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("DARK_MODE", isDarkMode)
            apply()
        }
    }

    private fun loadDarkModeState(): Boolean {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getBoolean("DARK_MODE", false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Esto hace que la actividad responda igual que si se presionara el botón de atrás
        return true
    }
}