package dev.sobhy.healthhubfordoctors.clinicfeature.data.remote

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.DayState
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.response.ClinicResponse
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.DayOfWeek

interface ClinicService {
    @POST("clinic/{doctorId}")
    suspend fun addClinic(
        @Path("doctorId") doctorId: Int,
        @Body clinic: ClinicRequest,
    ): Response<ClinicResponse>

    @PUT("clinic/{doctorId}")
    suspend fun updateClinic(
        @Path("doctorId") doctorId: Int,
        @Body clinic: ClinicRequest,
    )

    @GET("clinic/by-doctor")
    suspend fun getClinics(
        @Query("doctorId") doctorId: Int,
    ): Response<List<ClinicResponse>>

    @GET("clinic/by-id")
    suspend fun getClinic(
        @Query("clinicId") id: Int,
    ): ClinicRepository

    @DELETE("clinic")
    suspend fun deleteClinic(
        @Query("id") id: Int,
    )

    // availability
    @POST("clinic/{clinicId}/availability")
    suspend fun setAvailability(
        @Path("clinicId") clinicId: Int,
        @Body availability: Map<DayOfWeek, DayState>,
    )
}
