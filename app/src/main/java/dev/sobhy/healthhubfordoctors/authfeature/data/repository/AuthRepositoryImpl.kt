package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import android.util.Log
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.model.Gender
import dev.sobhy.healthhubfordoctors.authfeature.domain.model.Specialty
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.format.DateTimeFormatter

class AuthRepositoryImpl(
    private val auth: FirebaseAuthDataSource,
    private val apiService: ApiService,
    private val authPreferences: AuthPreferencesRepository,
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            // Firebase authentication
            val authResult =
                auth.signUpWithEmailPassword(registerRequest.email, registerRequest.password)
            val userId = authResult.user?.uid
            if (userId == null) {
                emit(Resource.Error("User ID is null"))
                return@flow
            }
            // prepare data for API call
            val doctorRequest = createDoctorRequest(registerRequest, userId)
            Log.d("DoctorRequest", doctorRequest.toString())
            // send data to API
            try {
                apiService.addDoctor(doctorRequest)
            } catch (e: Exception) {
                authResult.user?.let { user -> auth.deleteAccount(user) }
                emit(Resource.Error("Registration failed"))
                return@flow
            }
            auth.sendEmailVerification(authResult.user!!)
            emit(Resource.Success("Registration successful"))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    private fun createDoctorRequest(
        registerRequest: RegisterRequest,
        userId: String,
    ): DoctorRequest {
        val dateOfBirth = registerRequest.dateOfBirth // Replace with your LocalDate object
        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val dateOfBirthString = dateOfBirth.format(dateFormatter)
        return DoctorRequest(
            id = userId,
            name = registerRequest.name,
            email = registerRequest.email,
            phoneNumber = registerRequest.phone,
            birthDate = dateOfBirthString,
            gender = Gender.valueOf(registerRequest.gender),
            profTitle = registerRequest.professionalTitle,
            specialty = Specialty(registerRequest.specialization),
        )
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<String>> {
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
            authPreferences.saveUserToken(userId)
            emit(Resource.Success("login successful"))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            auth.signOut()
            authPreferences.clearUserToken()
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
