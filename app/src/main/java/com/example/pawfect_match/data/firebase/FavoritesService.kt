package com.example.pawfect_match.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object FavoritesService {
    private val db = Firebase.firestore

    /**
     * Get a list of IDs from favourites.
     */
    suspend fun getFavorites(userId: String): List<String> {
        val snapshot = db.collection("favorites").document(userId).get().await()
        return snapshot.get("ids") as? List<String> ?: emptyList()
    }

    /**
     * Add ID of an animal in favourites.
     */
    suspend fun addFavorite(userId: String, petId: String) {
        val ref = db.collection("favorites").document(userId)
        db.runTransaction { tx ->
            val data = tx.get(ref)
            val current = data.get("ids") as? MutableList<String> ?: mutableListOf()
            if (!current.contains(petId)) {
                current.add(petId)
                tx.set(ref, mapOf("ids" to current))
            }
        }.await()
    }

    /**
     * Delete ID of an animal from favourites.
     */
    suspend fun removeFavorite(userId: String, petId: String) {
        val ref = db.collection("favorites").document(userId)
        db.runTransaction { tx ->
            val data = tx.get(ref)
            val current = data.get("ids") as? MutableList<String> ?: mutableListOf()
            current.remove(petId)
            tx.set(ref, mapOf("ids" to current))
        }.await()
    }

    suspend fun toggleFavorite(userId: String, petId: String) {
        val current = getFavorites(userId)
        if (petId in current) removeFavorite(userId, petId) else addFavorite(userId, petId)
    }
}
