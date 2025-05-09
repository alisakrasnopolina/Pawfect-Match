package com.example.pawfect_match.data.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import com.example.pawfect_match.data.model.User
import kotlinx.coroutines.tasks.await

/**
 * Manages Firestore operations for user data.
 */
object UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    /**
     * Saves user data to Firestore under 'users/{uid}'.
     */
    suspend fun saveUser(user: User) {
        user.uid?.let {
            usersCollection.document(it).set(user).await()
        }
    }

    /**
     * Fetches user data from Firestore by UID.
     *
     * @return User if found, null otherwise.
     */
    suspend fun getUser(uid: String): User? {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            Log.d("Firestore", "Data: ${snapshot.data}")
            snapshot.data?.let {
                User(
                    uid = it["uid"] as? String ?: "",
                    name = it["name"] as? String ?: "",
                    email = it["email"] as? String ?: "",
                    phone = it["phone"] as? String ?: "",
                    gender = it["gender"] as? String ?: "",
                    birthdate = it["birthdate"] as? String ?: "",
                    profileImageUrl = it["profileImageUrl"] as? String ?: "",
                    preferredAnimalType = it["preferredAnimalType"] as? String
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateField(uid: String, field: String, value: Any) {
        db.collection("users").document(uid)
            .update(field, value)
            .await()
    }

    suspend fun updateFields(uid: String, data: Map<String, Any>) {
        db.collection("users").document(uid)
            .update(data)
            .await()
    }
}

