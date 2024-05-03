package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("doctor")
    suspend fun addDoctor(
        @Body doctorRequest: DoctorRequest,
    )
}
