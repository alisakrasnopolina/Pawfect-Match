package com.example.pawfect_match.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * Manages Firebase Authentication functions such as sign up, sign in and sign out.
 */
object FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Registers a new user with email and password.
     *
     * @return FirebaseUser on success, null on failure.
     */
    suspend fun register(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Logs in an existing user.
     *
     * @return FirebaseUser on success, null on failure.
     */
    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Logs out the current user.
     */
    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    /**
     * Returns the currently logged in user, or null if not logged in.
     */
    fun currentUser(): FirebaseUser? = auth.currentUser
}