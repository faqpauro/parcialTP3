package com.example.parcialtp3.activities

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.parcialtp3.R
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.repository.UserRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PerfilActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    @Inject
    lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inserción de nombre de usuario
        val user = sharedViewModel.getUserData(this@PerfilActivity)
        val userName = findViewById<TextView>(R.id.perfil_name)
        userName.text = user?.username

        // Inserción de imagen
        val imagenPerfil = user?.avatar_url

        if(imagenPerfil!=null){
            val patronUrl = Patterns.WEB_URL
            var esValida = patronUrl.matcher(imagenPerfil).matches()
            val esImagen = imagenPerfil.endsWith(".jpg", ignoreCase = true) || imagenPerfil.endsWith(".png", ignoreCase = true)
            if(esValida && esImagen){
                Glide.with(this).load(imagenPerfil).into(findViewById<ImageView>(R.id.imagen_foto_perfil))
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setTitle("Perfil")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val buttonUploadPhoto = findViewById<Button>(R.id.btn_subir_foto)
        buttonUploadPhoto.setOnClickListener {
            showInputDialog(user)
        }
    }

    private fun setAppTheme() {
        val isDarkMode = sharedViewModel.getUserData(this)?.darkModeSelection ?: false
        if (isDarkMode) {
            setTheme(R.style.Theme_ParcialTP3_Dark)
        } else {
            setTheme(R.style.Theme_ParcialTP3)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Esto hace que la actividad responda igual que si se presionara el botón de atrás
        return true
    }

    private fun showInputDialog(user: User?) {
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
            val patronUrl = Patterns.WEB_URL
            var esValida = patronUrl.matcher(imageUrl).matches()
            val esImagen = imageUrl.endsWith(".jpg", ignoreCase = true) || imageUrl.endsWith(".png", ignoreCase = true)
            if (imageUrl.isNotEmpty()) {
                if(esValida && esImagen){
                    lifecycleScope.launch {
                        if (userRepository.updateUserImage(imageUrl, user?.id)){
                            Toast.makeText(this@PerfilActivity, "Imagen actualizada con éxito", Toast.LENGTH_SHORT).show()
                            // actualizar usuario del viewmodel
                            if (user != null) {
                                sharedViewModel.setUserData(this@PerfilActivity, user.copy(avatar_url = imageUrl))
                            }
                        } else {
                            Toast.makeText(this@PerfilActivity, "Ocurrió un error al actualizar la imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.dismiss()
                    Glide.with(this).load(imageUrl).into(findViewById<ImageView>(R.id.imagen_foto_perfil))
                } else {
                    editTextImageUrl.error = "La URL debe ser válida y de una imagen."
                }

            } else {
                editTextImageUrl.error = "¡No puede estar vacío!"
            }
        }
    }
}