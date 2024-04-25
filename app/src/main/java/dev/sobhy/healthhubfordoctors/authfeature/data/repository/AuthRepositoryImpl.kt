package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FireStoreDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FireStoreDataSourceImpl
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSourceImpl
import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val auth: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(),
    private val firestore: FireStoreDataSource = FireStoreDataSourceImpl(),
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<UserDetailsModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = auth.signUpWithEmailPassword(registerRequest.email, registerRequest.password)
                val userId = result.user?.uid
                if (userId == null) {
                    emit(Resource.Error("User ID is null"))
                    return@flow
                }
                val userDetails =
                    UserDetailsModel(
                        id = userId,
                        name = registerRequest.name,
                        email = registerRequest.email,
                        phone = registerRequest.phone,
                        gender = registerRequest.gender,
                        dateOfBirth = registerRequest.dateOfBirth,
                        specialization = registerRequest.specialization,
                        professionalTitle = registerRequest.professionalTitle,
                        createdAt = System.currentTimeMillis(),
                    )
                // save user details to firestore
                firestore.saveUserData(result.user!!, userDetails)
                auth.sendEmailVerification(result.user!!)
                emit(Resource.Success(userDetails))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<UserDetailsModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun forgetPassword(email: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }
}
