package com.example.parcialtp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.parcialtp3.R
import com.example.parcialtp3.data.Repository
import com.example.parcialtp3.repository.DogRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Publicacion.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class Publicacion : Fragment() {

    @Inject
    lateinit var dogRepository: DogRepository
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publicacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPostDog: Button = view.findViewById(R.id.btnPostDog)
        btnPostDog.setOnClickListener {

            val gender: String? = null
            val editTextBreed: EditText = view.findViewById(R.id.editTextRaza)
            val editTextSubBreed: EditText = view.findViewById(R.id.editTextSubRaza)
            val editTextAge: EditText = view.findViewById(R.id.editTextEdad)
            val stringAge: String = editTextAge.toString()
            val editTextWeight: EditText = view.findViewById(R.id.editTextPeso)
            val weightString: String = editTextWeight.text.toString()
            val editTextName: EditText = view.findViewById(R.id.editTextNombre)
            val editTextLocation: EditText = view.findViewById(R.id.editTextUbicacion)
            val editTextDescription: EditText = view.findViewById(R.id.editTextDescripcion)
            val editTextOwnerName: EditText = view.findViewById(R.id.editTextNombreDueño)
            val editTextOwnerContact: EditText = view.findViewById(R.id.editTextContactoDueño)
            val radioGroup: RadioGroup = view.findViewById(R.id.radioGroupGenero)
            val selectRadioButton: RadioButton = view.findViewById(radioGroup.checkedRadioButtonId)

            // Verifica si se seleccionó un RadioButton
            if (selectRadioButton != null) {
                val gender = if (selectRadioButton.id == R.id.radioButtonMacho) {
                    "Macho"
                } else {
                    "Hembra"
                }

                if (editTextBreed.text.toString().isEmpty()
                    || editTextSubBreed.text.toString().isEmpty()
                    || editTextAge.text.isEmpty()
                    || editTextWeight.text.isEmpty()
                    || editTextName.text.isEmpty()
                    || editTextLocation.text.isEmpty()
                    || editTextDescription.text.isEmpty()
                    || editTextOwnerContact.text.isEmpty()
                    || editTextOwnerName.text.isEmpty()
                ) {
                    // Mostrar advertencia de que faltan campos por completar
                    //Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    dogRepository.createNewDog(
                        editTextName.text.toString(),
                        editTextBreed.text.toString(),
                        editTextSubBreed.text.toString(),
                        gender,
                        weightString.toDouble(),
                        editTextLocation.text.toString(),
                        editTextDescription.text.toString(),
                        0,
                        stringAge.toInt()
                    )
                }
            }
        }
    }
}