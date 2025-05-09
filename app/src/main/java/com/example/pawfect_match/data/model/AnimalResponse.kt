package com.example.pawfect_match.data.model

data class AnimalResponse(val animal: AnimalDto)

data class AnimalDto(
    val id: Int,
    val name: String,
    val breeds: Breeds,
    val distance: Double?,
    val photos: List<Photo>?,
    val gender: String?,
    val age: String?,
    val size: String?,
    val shelterAddress: String?,
    val description: String?,
    val traits: String?,
    val adoptionInfo: String?
)

data class Breeds(val primary: String?)
data class Photo(val medium: String?)

fun AnimalDto.toPet() = Pet(
    id = id.toString(),
    name = name ?: "Unknown",
    breed = breeds.primary ?: "Unknown",
    distance = "${distance ?: "?"} km",
    imageUrl = photos?.firstOrNull()?.medium ?: "",
    gender = gender ?: "Unknown",
    age = age ?: "Unknown",
    size = size ?: "Unknown",
    shelterAddress = shelterAddress ?: "Not provided",
    description = description ?: "",
    traits = traits ?: "",
    adoptionInfo = adoptionInfo ?: ""
)

