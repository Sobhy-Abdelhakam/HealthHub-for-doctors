package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DoctorService {
    @POST("doctor")
    suspend fun addDoctor(
        @Body doctorRequest: DoctorRequest,
    )

    @GET("doctor/{id}")
    suspend fun getDoctor(
        @Path("id") id: Int,
    ): DoctorResponse
}
