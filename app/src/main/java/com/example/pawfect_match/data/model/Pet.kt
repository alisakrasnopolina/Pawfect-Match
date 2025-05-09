package com.example.pawfect_match.data.model


/**
 * Represents a simple pet object used in listings.
 *
 * @param name Pet's name.
 * @param breed Pet's breed.
 * @param distance Distance from the user in kilometers.
 * @param imageUrl URL to the pet's photo.
 */
data class Pet(
    val id: String,
    val name: String,
    val breed: String,
    val distance: String,
    val imageUrl: String,
    val gender: String,
    val age: String,
    val size: String,
    val shelterAddress: String? = null,
    val description: String? = null,
    val traits: String? = null,
    val adoptionInfo: String? = null
)
