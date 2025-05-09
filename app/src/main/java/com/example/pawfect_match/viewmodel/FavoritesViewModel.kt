package com.example.pawfect_match.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_match.data.firebase.FavoritesService
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.data.model.Pet
import com.example.pawfect_match.repository.PetfinderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val _favoriteIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds = _favoriteIds.asStateFlow()

    fun loadFavorites() {
        val userId = FirebaseAuthManager.currentUser()?.uid ?: return
        Log.d("currentUser", "UID = $userId")
        viewModelScope.launch {
            _favoriteIds.value = FavoritesService.getFavorites(userId)
            Log.d("loadFavorites", "IDs: ${_favoriteIds.value.joinToString()}")
        }
    }

    fun toggleFavorite(petId: String) {
        val userId = FirebaseAuthManager.currentUser()?.uid ?: return
        viewModelScope.launch {
            if (petId in _favoriteIds.value) {
                FavoritesService.removeFavorite(userId, petId)
                _favoriteIds.value = _favoriteIds.value - petId
            } else {
                FavoritesService.addFavorite(userId, petId)
                _favoriteIds.value = _favoriteIds.value + petId
            }
        }
    }
}

//    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
//    val pets = _pets.asStateFlow()
//
//    fun loadFavoritesWithDetails() {
//        val userId = FirebaseAuthManager.currentUser()?.uid ?: return
//        viewModelScope.launch {
//            val favoriteIds = FavoriteService.getFavorites(userId)
//
//            // 👇 Получить детальную инфу по каждому ID
//            val petList = favoriteIds.mapNotNull { id ->
//                try {
//                    PetfinderRepository.getPetById(id)
//                } catch (e: Exception) {
//                    null // если не найден — пропустить
//                }
//            }
//
//            _pets.value = petList
//        }
//    }
//
//    fun toggleFavorite(petId: String) {
//        val userId = FirebaseAuthManager.currentUser()?.uid ?: return
//        viewModelScope.launch {
//            if (_pets.value.any { it.id == petId }) {
//                FavoriteService.removeFavorite(userId, petId)
//                _pets.value = _pets.value.filterNot { it.id == petId }
//            } else {
//                FavoriteService.addFavorite(userId, petId)
//                // Опционально: заново подгрузить список
//            }
//        }
//    }
//}