package dev.sobhy.healthhubfordoctors.authfeature.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseAuthDataSource {
    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): AuthResult {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return result
    }

    override suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): AuthResult {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return result
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun passwordReset(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().await()
    }

    override suspend fun deleteAccount(user: FirebaseUser) {
        user.delete().await()
    }
}
