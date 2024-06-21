package dev.sobhy.healthhubfordoctors.clinicfeature.data

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ClinicService {
    @POST("clinic")
    suspend fun addClinic(
        @Body clinic: Clinic,
    ): Response<Clinic>

    @PUT("clinic")
    suspend fun updateClinic(
        @Query("id") id: Int,
        @Body clinic: Clinic,
    )

    @GET("clinics")
    suspend fun getClinics(): List<Clinic>

    @GET("clinic")
    suspend fun getClinic(id: Int): Clinic

    @DELETE("clinic")
    suspend fun deleteClinic(
        @Query("id") id: Int,
    )
}
