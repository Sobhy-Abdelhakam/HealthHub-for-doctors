package dev.sobhy.healthhubfordoctors.authfeature.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.data.request.EmailRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.LoginRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.ResetPassRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.UpdatePasswordRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register/doctor")
    suspend fun register(
        @Body request: RegisterRequest,
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): AuthResponse

    @POST("auth/forgot-password")
    suspend fun sendOtp(
        @Body emailRequest: EmailRequest,
    ): Response<String>

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body resetPassRequest: ResetPassRequest,
    ): Response<String>

    @POST("auth/update-password")
    suspend fun updatePassword(
        @Body updatePasswordRequest: UpdatePasswordRequest,
    ): Response<String>
}
