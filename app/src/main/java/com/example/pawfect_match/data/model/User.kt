package com.example.pawfect_match.data.model

/**
 * Data class representing a user stored in Firestore.
 */
data class User(
    val uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    val preferredAnimalType: String? = null,
    val birthdate: String? = null,
    val profileImageUrl: String? = null
)
