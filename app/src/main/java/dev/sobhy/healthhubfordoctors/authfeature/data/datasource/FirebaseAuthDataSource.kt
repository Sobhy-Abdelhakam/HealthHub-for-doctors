package dev.sobhy.healthhubfordoctors.authfeature.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthDataSource {
    // firebase functions
    suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): AuthResult

    suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): AuthResult

    suspend fun signOut()

    suspend fun passwordReset(email: String)

    suspend fun sendEmailVerification(user: FirebaseUser)

    suspend fun deleteAccount(user: FirebaseUser)
}
