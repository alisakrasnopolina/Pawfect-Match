package com.example.pawfect_match.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.data.model.User
import com.example.pawfect_match.data.firebase.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for handling authentication and user state.
 */
class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthStatus>(AuthStatus.Idle)
    val authState = _authState.asStateFlow()

    /**
     * Attempts to register a new user.
     */
    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthStatus.Loading
                val user = FirebaseAuthManager.register(email, password)
                if (user != null) {
                    val newUser = User(
                        uid = user.uid,
                        email = user.email ?: "",
                        name = "", // или введи на форме позже
                        phone = "",
                        gender = "",
                        birthdate = "",
                        profileImageUrl = ""
                    )
                    UserRepository.saveUser(newUser)
                    _authState.value = AuthStatus.Success(user.uid)
                } else {
                    _authState.value = AuthStatus.Error("Registration failed. User is null.")
                }
            } catch (e: Exception) {
                _authState.value = AuthStatus.Error(e.message ?: "Registration failed")
            }
        }
    }

    /**
     * Attempts to log in an existing user.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthStatus.Loading
            val user = FirebaseAuthManager.login(email, password)
            if (user != null) {
                _authState.value = AuthStatus.Success(user.uid)
            } else {
                _authState.value = AuthStatus.Error("Login failed")
            }
        }
    }

    fun logout() {
        FirebaseAuthManager.logout()
        _authState.value = AuthStatus.Idle
    }

    fun resetAuthState() {
        _authState.value = AuthStatus.Idle
    }

    sealed class AuthStatus {
        object Idle : AuthStatus()
        object Loading : AuthStatus()
        data class Success(val uid: String) : AuthStatus()
        data class Error(val message: String) : AuthStatus()
    }
}