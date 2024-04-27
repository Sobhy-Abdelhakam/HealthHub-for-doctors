package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FireStoreDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val auth: FirebaseAuthDataSource,
    private val firestore: FireStoreDataSource,
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<UserDetailsModel>> {
        return flow {
            emit(Resource.Loading())
            val result =
                auth.signUpWithEmailPassword(registerRequest.email, registerRequest.password)
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
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<UserDetailsModel>> {
        return flow {
            emit(Resource.Loading())
            val loginResult = auth.signInWithEmailPassword(email, password)
            val userId = loginResult.user?.uid
            if (userId == null) {
                emit(Resource.Error("User ID is null"))
                return@flow
            }
            if (!loginResult.user!!.isEmailVerified) {
                auth.sendEmailVerification(loginResult.user!!)
                emit(Resource.Error("Email is not verified"))
                return@flow
            }
            // get user details from firestore

            val userDetails = firestore.getUserData(loginResult.user!!)
            if (userDetails == null) {
                emit(Resource.Error("User details are null"))
                return@flow
            }
            emit(Resource.Success(userDetails))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            auth.signOut()
            emit(Resource.Success(Unit))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun forgetPassword(email: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            auth.passwordReset(email)
            emit(Resource.Success("Password reset link sent to your email"))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }
}
