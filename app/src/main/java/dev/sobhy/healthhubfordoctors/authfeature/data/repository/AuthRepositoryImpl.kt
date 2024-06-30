package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import android.util.Log
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.AuthService
import dev.sobhy.healthhubfordoctors.authfeature.data.request.LoginRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val authPreferences: AuthPreferencesRepository,
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())

            val authResult = authService.register(registerRequest)
            if (!authResult.ok) {
                emit(Resource.Error(authResult.error ?: "An error occurred"))
                return@flow
            }
            emit(Resource.Success(authResult.message!!))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val loginRequest = LoginRequest(email, password)

            val loginResult = authService.login(loginRequest)
            if (!loginResult.ok) {
                Log.e("login", loginResult.error ?: "An error occurred")
                emit(Resource.Error(loginResult.error ?: "An error occurred"))
                return@flow
            }
            Log.e("login", loginResult.message ?: "An error occurred")
            authPreferences.saveUserToken(loginResult.body?.accessToken!!)
            authPreferences.saveUserId(loginResult.body.userId)
            emit(Resource.Success(loginResult.message!!))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            authPreferences.clearUserToken()
            authPreferences.clearUserId()
            emit(Resource.Success(Unit))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun forgetPassword(email: String): Flow<Resource<String>> {
        return flow {
            // TODO: Not implemented yet
        }
//            emit(Resource.Loading())
//            auth.passwordReset(email)
//            emit(Resource.Success("Password reset link sent to your email"))
//        }.catch {
//            emit(Resource.Error(it.message ?: "An error occurred"))
//        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): Flow<Resource<String>> {
        return flow<Resource<String>> {
            // TODO: Not implemented yet
//            emit(Resource.Loading())
//            val user = FirebaseAuth.getInstance().currentUser
//            if (user == null) {
//                emit(Resource.Error("User not logged in"))
//                return@flow
//            }
//
//            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
//            user.reauthenticate(credential).await()
//            user.updatePassword(newPassword).await()
//            emit(Resource.Success("Password changed successfully"))
//        }.catch {
//            emit(Resource.Error("Failed to change password: ${it.message}"))
        }
    }
}
