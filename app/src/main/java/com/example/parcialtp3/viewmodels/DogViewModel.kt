package com.example.parcialtp3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parcialtp3.entities.Dog
import com.example.parcialtp3.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val dogRepository: DogRepository,
    ) : ViewModel(){

    private val _filter = MutableLiveData<String?>()
    val filter: LiveData<String?> = _filter

    fun setFilter(filter: String){
        _filter.value = filter
    }

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>> = _dogList

    private val _availableBreeds = MutableLiveData<List<String>>()
    val availableBreeds: LiveData<List<String>> = _availableBreeds

    fun setDogList(dogList: List<Dog>){
        _dogList.value = dogList
    }
    }