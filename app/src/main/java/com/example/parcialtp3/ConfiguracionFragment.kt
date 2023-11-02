package com.example.parcialtp3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

class ConfiguracionFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuracion, container, false)

        val darkModeSwitch: Switch = view.findViewById(R.id.darkModeSwitch)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // activar dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveDarkModeState(true)
            } else {
                // desactivar dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveDarkModeState(false)
            }
        }

        // Initialize the switch position based on the saved state
        darkModeSwitch.isChecked = loadDarkModeState()

        return view
    }

    private fun saveDarkModeState(isDarkMode: Boolean) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("DARK_MODE", isDarkMode)
            apply()
        }
    }

    private fun loadDarkModeState(): Boolean {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("DARK_MODE", false)
    }

}