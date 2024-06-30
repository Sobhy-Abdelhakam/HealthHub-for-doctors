package dev.sobhy.healthhubfordoctors.authfeature.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.data.request.LoginRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.request.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.data.response.AuthResponse
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
}
