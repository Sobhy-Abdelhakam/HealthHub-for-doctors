package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.AuthService
import dev.sobhy.healthhubfordoctors.authfeature.data.request.EmailRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.LoginRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.ResetPassRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.UpdatePasswordRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.response.AuthResponse
import dev.sobhy.healthhubfordoctors.authfeature.data.response.ErrorResponse
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val authPreferences: AuthPreferencesRepository,
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                val authResult = authService.register(registerRequest)

                if (!authResult.ok) {
                    val errorMessage = authResult.error ?: "An error occurred"
                    Log.e("register", errorMessage)
                    emit(Resource.Error(errorMessage))
                    return@flow
                }

                val successMessage = authResult.message ?: "Registration successful"
                Log.e("register", successMessage)
                emit(Resource.Success(successMessage))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.e("HttpException: ", errorMessage)
                emit(Resource.Error(errorMessage))
            } catch (e: Exception) {
                Log.e("Exception: ", e.message ?: "An error occurred")
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }.catch { exception ->
            Log.e("catch: ", exception.message ?: "An error occurred")
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                val loginRequest = LoginRequest(email, password)
                val loginResult = authService.login(loginRequest)

                if (!loginResult.ok) {
                    val errorMessage = loginResult.error ?: "An error occurred"
                    Log.e("login", errorMessage)
                    emit(Resource.Error(errorMessage))
                    return@flow
                }

                val loginBody = loginResult.body
                if (loginBody == null) {
                    val errorMessage = "Login response body is null"
                    Log.e("login", errorMessage)
                    emit(Resource.Error(errorMessage))
                    return@flow
                }

                authPreferences.saveUserToken(loginBody.accessToken!!)
                authPreferences.saveUserId(loginBody.userId)

                val successMessage = loginResult.message ?: "Login successful"
                Log.e("login", successMessage)
                emit(Resource.Success(successMessage))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.e("HttpException: ", errorMessage)
                emit(Resource.Error(errorMessage))
            } catch (e: Exception) {
                Log.e("Exception: ", e.message ?: "An error occurred")
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }.catch { exception ->
            Log.e("catch: ", exception.message ?: "An error occurred")
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }

    // Function to parse the error message from the error body
    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val errorResponse = Gson().fromJson(errorBody, AuthResponse::class.java)
            errorResponse.error ?: "Unknown error"
        } catch (e: JsonSyntaxException) {
            "Unknown error"
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

    override suspend fun sendOtp(email: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                val sendOtp = authService.sendOtp(EmailRequest(email))
                if (sendOtp.isSuccessful) {
                    val responseBody = sendOtp.body()
                    if (responseBody != null) {
                        Log.e("response: ", responseBody)
                        emit(Resource.Success(responseBody))
                    } else {
                        Log.e("Empty response body", "Otp Sending Success")
                        emit(Resource.Success("Otp Sending Success"))
                    }
                } else {
                    val errorBody = sendOtp.errorBody()?.string() ?: "Unknown error"
                    Log.e("Error response: ", errorBody)
                    // Parse the error response
                    val errorMessage =
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } catch (e: JsonSyntaxException) {
                            "Unknown error"
                        }

                    emit(Resource.Error(errorMessage))
                }
            } catch (e: Exception) {
                Log.e("Exception: ", e.message ?: "An error occurred")
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun resetPassword(resetPassRequest: ResetPassRequest): Flow<Resource<String>> {
        return flow {
            val resetResult = authService.resetPassword(resetPassRequest)
            if (!resetResult.isSuccessful) {
                emit(Resource.Error(resetResult.body() ?: "Error occurred"))
                return@flow
            }
            emit(Resource.Success(resetResult.body() ?: "Success"))
        }.catch {
            emit(Resource.Error(it.message ?: "an error occurred"))
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): Flow<Resource<String>> {
        return flow {
            val updateRequest = UpdatePasswordRequest(currentPassword, newPassword)
            val updatePassResponse = authService.updatePassword(updateRequest)
            if (!updatePassResponse.isSuccessful) {
                emit(Resource.Error(updatePassResponse.body() ?: "Error occurred"))
                return@flow
            }
            emit(Resource.Success(updatePassResponse.body() ?: "success"))
        }.catch {
            emit(Resource.Error(it.message ?: "Error occurred"))
        }
    }
}
