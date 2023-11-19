package com.example.parcialtp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.R
import com.example.parcialtp3.adapters.DogAdapter
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Favoritos : Fragment() {

    @Inject
    lateinit var dogRepository: DogRepository
    private lateinit var dogAdapter: DogAdapter
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FAVORITE RECYCLE VIEW
        val favoriteRecycleView : RecyclerView = view.findViewById(R.id.recycler_favoritos)
        val favoritesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        favoriteRecycleView.layoutManager = favoritesLayoutManager
        dogAdapter = DogAdapter(requireContext(), mutableListOf(),dogRepository,sharedViewModel)
        favoriteRecycleView.adapter = dogAdapter
        dogAdapter.setOnItemClickListener(object : DogAdapter.OnItemClickListener{
            override fun onItemClick(dog: Dog) {
                handleDogClick(dog)
            }
        })
        val user: User? = sharedViewModel.getUserData(requireContext())
        if (user != null) {
            val favoriteList : List<Dog> = dogRepository.getFavoriteDogs(user.id)
            dogAdapter.setDogs(favoriteList)
        }
    }

    fun handleDogClick(dog: Dog){
        //dogViewModel.setFilter(dog.breed)
        //NAVEGAR A PERRO SOLO
    }


}