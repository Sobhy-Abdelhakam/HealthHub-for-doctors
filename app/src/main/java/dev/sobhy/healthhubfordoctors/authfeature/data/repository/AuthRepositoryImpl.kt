package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import com.google.firebase.auth.FirebaseAuth
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
) : AuthRepository {
    override fun login(
        email: String,
        password: String,
    ) = flow {
        emit(value = Resource.Loading())
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(value = Resource.Success(data = result))
    }.catch {
        emit(value = Resource.Error(message = it.message.toString()))
    }
}
