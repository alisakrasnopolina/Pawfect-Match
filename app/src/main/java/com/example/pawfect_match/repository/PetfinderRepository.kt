package com.example.pawfect_match.repository

import android.util.Log
import com.example.pawfect_match.data.remote.ApiClient
import com.example.pawfect_match.data.model.Pet
import com.example.pawfect_match.data.model.toPet

object PetfinderRepository {
    private var token: String? = null

    private suspend fun ensureToken(): String {
        if (token == null) {
            val response = ApiClient.api.getAccessToken(
                clientId = "r4gKGKhog0jXHipJK8RsreDx3iGtIo8foqFhyNibbsScDzWwC4",
                clientSecret = "waMgs0Rn13huXIzIv3L2UhOza30tJwC2EHPa1Go2"
            )
            token = "${response.token_type} ${response.access_token}"
            Log.d("TOKEN", "Fetched token: $token")
        }
        return token!!
    }

    suspend fun getPetById(id: String): Pet? {
        val token = ensureToken()
        val response = ApiClient.api.getAnimalById(id, authHeader = token)
        return response.animal.toPet()
    }

    suspend fun getPetsByType(type: String): List<Pet> {
        val token = ensureToken()
        val validType = when (type) {
            "Dogs" -> "Dog"
            "Cats" -> "Cat"
            "Rabbits" -> "Rabbit"
            "Small&Furry" -> "Small & Furry"
            "Birds" -> "Bird"
            "Fish" -> "Scales, Fins & Other"
            "Barnyard" -> "Barnyard"
            else -> "Dog"
        }
        val response = ApiClient.api.searchAnimalsByType(validType, token)

        response.animals.forEachIndexed { index, animal ->
            Log.d("PetfinderAPI", "Animal $index: ${animal.name}, ${animal.breeds.primary}, ${animal.distance}, ${animal.photos?.firstOrNull()?.medium}")
        }

        return response.animals.map { it.toPet() }
    }

    suspend fun getPetsByIds(ids: List<String>): List<Pet> {
        return ids.mapNotNull { getPetById(it) }
    }

}
