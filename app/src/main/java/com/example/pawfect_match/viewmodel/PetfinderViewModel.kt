package com.example.pawfect_match.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_match.data.model.Pet
import com.example.pawfect_match.repository.PetfinderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetfinderViewModel : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets
    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet: StateFlow<Pet?> = _selectedPet

    fun setSelectedPet(pet: Pet) {
        _selectedPet.value = pet
    }

    fun loadPetsByType(type: String) {
        viewModelScope.launch {
            try {
                val result = PetfinderRepository.getPetsByType(type)
                Log.d("Petfinder", "Loaded ${result.size} pets")
                _pets.value = result
            } catch (e: Exception) {
                Log.e("Petfinder", "Error loading pets: ${e.message}")
                _pets.value = emptyList() // or show error
            }
        }
    }

    fun loadPetsByIds(ids: List<String>) {
        viewModelScope.launch {
            try {
                val result = PetfinderRepository.getPetsByIds(ids)
                _pets.value = result
            } catch (e: Exception) {
                _pets.value = emptyList()
            }
        }
    }

//    fun loadPetsNearby(location: String) {
//        viewModelScope.launch {
//            try {
//                val result = PetfinderRepository.getPetsNear(location)
//                _pets.value = result
//            } catch (e: Exception) {
//                _pets.value = emptyList()
//            }
//        }
//    }
}