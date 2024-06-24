package dev.sobhy.healthhubfordoctors.clinicfeature.data

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.ClinicResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ClinicService {
    @POST("clinic/{token}")
    suspend fun addClinic(
        @Path("token") token: String,
        @Body clinic: ClinicRequest,
    ): Response<ClinicResponse>

    @PUT("clinic")
    suspend fun updateClinic(
        @Query("id") id: Int,
        @Body clinic: ClinicRequest,
    )

    @GET("clinics")
    suspend fun getClinics(): List<ClinicRequest>

    @GET("clinic")
    suspend fun getClinic(id: Int): ClinicRequest

    @DELETE("clinic")
    suspend fun deleteClinic(
        @Query("id") id: Int,
    )
}
