package com.example.parcialtp3.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.entities.Filter
import com.example.parcialtp3.R
import com.example.parcialtp3.adapters.DogAdapter
import com.example.parcialtp3.adapters.FilterAdapter
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.entities.UserFavorite
import com.example.parcialtp3.repository.DogRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var breedFilter: String
@AndroidEntryPoint
class Home : Fragment() {

    @Inject
    lateinit var dogRepository: DogRepository
    private lateinit var dogAdapter: DogAdapter
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var dogList : List<Dog>
    private lateinit var filterList : List<Filter>
    private var userId:Int = -1
    private var dogId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //BUSCADOR
        val searchBreed:AutoCompleteTextView = view.findViewById(R.id.searchBreed)
        val searchButton: Button = view.findViewById(R.id.buttonSearch)
        searchButton.setOnClickListener{
            val selectedBreed = searchBreed.text.toString()
            handleSearchBreed(selectedBreed)
        }

        //AUTOCOMPLETE
        val breedSuggestions = dogRepository.getBreedList().map { it.title }.toTypedArray()
        val breedAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedSuggestions)
        searchBreed.setAdapter(breedAdapter)
        searchBreed.setOnItemClickListener { _, _, position, _ ->
            val selectedBreed = breedAdapter.getItem(position)
            handleSearchBreed(selectedBreed.toString())
        }



        //PERRO RECYCLE VIEW
        val dogRecyclerView: RecyclerView = view.findViewById(R.id.dog_recycler_view)
        val dogLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        dogRecyclerView.layoutManager = dogLayoutManager
        dogAdapter = DogAdapter(requireContext(), mutableListOf())
        dogRecyclerView.adapter = dogAdapter
        dogRepository.clearAllData()
        dogRepository.createDogs()

        dogAdapter.setOnItemClickListener(object : DogAdapter.OnItemClickListener{
            override fun onItemClick(dog: Dog) {
                handleDogClick(dog)
            }
        })

        lifecycleScope.launch {
           dogList = dogRepository.getAllDogs()
            dogAdapter.setDogs(dogList)
        }

        /*   favoriteMark.setOnClickListener{
               if(userId!=-1 && dogId != -1){
                   val userFavorite = UserFavorite(0, userId, dogId)
               }
           }*/

        //FILTRO RECYCLE VIEW
        val filterRecyclerView: RecyclerView = view.findViewById(R.id.recycle_filter)
        val filterLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        filterRecyclerView.layoutManager = filterLayoutManager
        filterAdapter = FilterAdapter(requireContext(), mutableListOf())
        filterRecyclerView.adapter = filterAdapter

        filterAdapter.setOnItemClickListener(object : FilterAdapter.OnItemClickListener{
            override fun onItemClick(filter: Filter) {
                breedFilter = filter.title
                handleFilterClick(filter)
            }
        })

        lifecycleScope.launch {
            filterList = dogRepository.getBreedList()
            filterAdapter.setFilters(filterList)
        }

    }
    fun handleDogClick(dog: Dog){
        //dogViewModel.setFilter(dog.breed)
        //NAVEGAR A PERRO SOLO
    }
    fun handleFilterClick(filter: Filter){
        var filterDogs = dogList.filter{it.breed == breedFilter}
        if(filterDogs.isEmpty()){
            filterDogs = dogList.filter{it.subBreed == breedFilter}
        }
        dogAdapter.setDogs(filterDogs)

    }

    fun handleSearchBreed(selectedBreed: String) {
        val filteredDogs = if (selectedBreed.isNotBlank()) {
            dogList.filter { it.breed == selectedBreed || it.subBreed == selectedBreed }
        } else {
            // Si no se selecciona una raza, muestra todos los perros
            dogList
        }
        dogAdapter.setDogs(filteredDogs)
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}