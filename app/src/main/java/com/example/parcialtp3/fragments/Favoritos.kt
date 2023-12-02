package com.example.parcialtp3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var emptyListTextView: TextView
    private lateinit var favoriteRecycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        emptyListTextView = view.findViewById(R.id.tv_empty_favorite_list)
        favoriteRecycleView = view.findViewById(R.id.recycler_favoritos)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val favoritesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favoriteRecycleView.layoutManager = favoritesLayoutManager
        dogAdapter = DogAdapter(requireContext(), mutableListOf(), dogRepository, sharedViewModel)
        favoriteRecycleView.adapter = dogAdapter

        dogAdapter.setOnItemClickListener(object : DogAdapter.OnItemClickListener {
            override fun onItemClick(dog: Dog) {
                handleDogClick(dog)
            }
        })

        dogAdapter.setOnFavoriteChangedListener(object : DogAdapter.OnFavoriteChangedListener {
            override fun onFavoriteChanged() {
                updateFavoriteList()
            }
        })

        updateFavoriteList()
    }

    private fun updateFavoriteList() {
        val user: User? = sharedViewModel.getUserData(requireContext())
        if (user != null) {
            val favoriteList: List<Dog> = dogRepository.getFavoriteDogs(user.id)
            dogAdapter.setDogs(favoriteList)

            if (favoriteList.isEmpty()) {
                emptyListTextView.visibility = View.VISIBLE
                favoriteRecycleView.visibility = View.GONE
            } else {
                emptyListTextView.visibility = View.GONE
                favoriteRecycleView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleDogClick(dog: Dog) {
        // Aquí va tu lógica para manejar el clic en un perro
        // Por ejemplo, abrir una nueva actividad con detalles del perro
    }
}
