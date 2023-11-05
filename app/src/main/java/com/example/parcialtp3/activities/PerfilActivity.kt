package com.example.parcialtp3.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.parcialtp3.R
import com.example.parcialtp3.viewmodels.SharedViewModel

class PerfilActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val isDarkMode = sharedViewModel.getDarkModeState(this)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setTitle("Perfil")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val buttonUploadPhoto = findViewById<Button>(R.id.btn_subir_foto)
        buttonUploadPhoto.setOnClickListener {
            showInputDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Esto hace que la actividad responda igual que si se presionara el botón de atrás
        return true
    }

    private fun showInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input_url, null)
        val editTextImageUrl = dialogView.findViewById<EditText>(R.id.editTextImageUrl)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Subir nueva foto")
            .setView(dialogView)
            .setPositiveButton("Aceptar", null) // Configuramos el botón pero lo sobrescribiremos más tarde para evitar que se cierre el diálogo
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()

        // Sobrescribimos el OnClickListener del botón para evitar que se cierre el diálogo si la URL está vacía
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val imageUrl = editTextImageUrl.text.toString().trim()
            if (imageUrl.isNotEmpty()) {
                // Aquí manejar la URL, por ej cargar la imagen con Glide o Picasso
                dialog.dismiss()
                Glide.with(this).load(imageUrl).into(findViewById<ImageView>(R.id.imagen_foto_perfil))
            } else {
                editTextImageUrl.error = "¡No puede estar vacío!"
            }
        }
    }
}