package com.example.parcialtp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.parcialtp3.R
import com.example.parcialtp3.data.Repository
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Publicacion : Fragment() {
    private val sharedViewModel: SharedViewModel by viewModels()
    @Inject
    lateinit var dogRepository: DogRepository
    lateinit var breedsList : List<String>
    lateinit var selectedBreed : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publicacion, container, false)
    }

    private fun isValidBreed(breed : String): Boolean{
        return breedsList.contains(breed)
    }
    private fun isValidSubBreed(breed: String, subBreed: String) : Boolean{
        var breedsAndSubBreeds : Map<String, List<String>> = emptyMap()
        var subBreedsForBreed : List<String> = emptyList()
        lifecycleScope.launch {
            breedsAndSubBreeds = dogRepository.getDogBreedsAndSubBreeds()
        }
        subBreedsForBreed = breedsAndSubBreeds[breed].orEmpty()
        return subBreedsForBreed.isEmpty() || subBreedsForBreed.contains(subBreed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = sharedViewModel.getUserData(requireContext())
        val editTextBreed: AutoCompleteTextView = view.findViewById(R.id.editTextRaza)
        val editTextSubBreed: AutoCompleteTextView = view.findViewById(R.id.editTextSubRaza)

        val usuario = view.findViewById<EditText>(R.id.editTextNombreDueño)
        usuario.hint = user?.username

        lifecycleScope.launch {
            val breedsAndSubBreeds = dogRepository.getDogBreedsAndSubBreeds()

            // Ahora, breedsAndSubBreeds contiene un mapa con razas y subrazas
            breedsList = breedsAndSubBreeds.keys.toList() // Lista de razas
            val subBreedsList = breedsAndSubBreeds.values.flatten() // Lista de subrazas
            val breedsAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, breedsList)
            editTextBreed.setAdapter(breedsAdapter)

            editTextBreed.setOnItemClickListener { _, _, position, _ ->
                selectedBreed = breedsAdapter.getItem(position).toString()
                if (selectedBreed != null) {
                    val subBreedsForSelectedBreed = breedsAndSubBreeds[selectedBreed]
                    val subBreedsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, subBreedsForSelectedBreed.orEmpty())
                    editTextSubBreed.setAdapter(subBreedsAdapter)
                    editTextSubBreed.isEnabled = subBreedsForSelectedBreed?.isNotEmpty() == true
                }
            }
        }


        val btnPostDog: Button = view.findViewById(R.id.btnPostDog)
        btnPostDog.setOnClickListener {

            val gender: String? = null
            val editTextAge: EditText = view.findViewById(R.id.editTextEdad)
            val stringAge: String = editTextAge.text.toString()
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
                    Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    if (isValidBreed(editTextBreed.text.toString()) && isValidSubBreed(selectedBreed.orEmpty(), editTextSubBreed.text.toString())) {
                        val user = sharedViewModel.getUserData(requireContext())
                        if(user!=null){
                            lifecycleScope.launch {
                                var age: Int = stringAge.toInt()
                                if (dogRepository.createNewDog(
                                        editTextName.text.toString(),
                                        editTextBreed.text.toString(),
                                        editTextSubBreed.text.toString(),
                                        gender,
                                        weightString.toDouble(),
                                        editTextLocation.text.toString(),
                                        editTextDescription.text.toString(),
                                        user.id,
                                        age
                                    )){
                                    Toast.makeText(requireContext(), "Perro agregado con éxito", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(requireContext(), "Hubo un error al cargar el perro", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(), "Hubo un error al cargar el perro", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}