package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("doctor")
    suspend fun addDoctor(
        @Body doctorRequest: DoctorRequest,
    )

    // clinic
    @POST("clinic/{token}")
    suspend fun addClinic(
        @Path("token") token: String,
        @Body clinic: Clinic,
    ): Response<Void>

    @PUT("clinic")
    suspend fun updateClinic(
        @Query("id") id: Int,
        @Body clinic: Clinic,
    )

    @GET("clinic/by-doctor")
    suspend fun getClinics(
        @Query("doctorId") token: String,
    ): Response<List<Clinic>>

    @GET("clinic")
    suspend fun getClinic(id: Int): Clinic

    @DELETE("clinic")
    suspend fun deleteClinic(
        @Query("id") id: Int,
    )

    // availability
    @POST("availability")
    suspend fun setAvailability(
        @Body availability: Availability,
    )
}
