package com.example.pawfect_match.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.data.firebase.UserRepository
import com.example.pawfect_match.data.model.User
import com.example.pawfect_match.viewmodel.AuthViewModel.AuthStatus

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        val uid = FirebaseAuthManager.currentUser()?.uid ?: return
        viewModelScope.launch {
            _user.value = UserRepository.getUser(uid)
        }
    }

    fun updateUser(user: User) {
        val uid = FirebaseAuthManager.currentUser()?.uid ?: return
        viewModelScope.launch {
            UserRepository.saveUser(user.copy(uid = uid))
            _user.value = user
        }
    }

    fun updateField(field: String, value: Any) {
        val uid = FirebaseAuthManager.currentUser()?.uid ?: return
        viewModelScope.launch {
            UserRepository.updateField(uid, field, value)
        }
    }

    fun updateFields(uid: String, fields: Map<String, Any>) {
        viewModelScope.launch {
            try {
                UserRepository.updateFields(uid, fields)
            } catch (e: Exception) {
            }
        }
    }

    fun getPreferredAnimalType(onResult: (String?) -> Unit) {
        val uid = FirebaseAuthManager.currentUser()?.uid
        Log.d("Debug", "UID: $uid")
        if (uid == null) {
            onResult(null)
            return
        }

        viewModelScope.launch {
            val user = UserRepository.getUser(uid)
            Log.d("Debug", "User: $user")
            onResult(user?.preferredAnimalType)
        }
    }

    fun clearUser() {
        _user.value = null
    }

}
