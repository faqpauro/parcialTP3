package com.example.parcialtp3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.ApiInterface.DogCeoApi
import com.example.parcialtp3.R
import com.example.parcialtp3.adapters.DogAdapter
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.viewmodels.DogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class Home : Fragment() {

    @Inject
    lateinit var dogRepository: DogRepository
    private lateinit var dogAdapter: DogAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            val dogList = dogRepository.getAllDogs()
            dogAdapter.setDogs(dogList)
        }

    }
    fun handleDogClick(dog: Dog){
        //dogViewModel.setFilter(dog.breed)
        //NAVEGAR A PERRO SOLO
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}