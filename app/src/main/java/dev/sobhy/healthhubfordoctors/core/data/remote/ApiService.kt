package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.DayState
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.ClinicResponse
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.DayOfWeek

interface ApiService {
    @POST("doctor")
    suspend fun addDoctor(
        @Body doctorRequest: DoctorRequest,
    )

    @GET("doctor/{token}")
    suspend fun getDoctor(
        @Path("token") token: String,
    ): DoctorResponse

    // clinic
    @POST("clinic/{token}")
    suspend fun addClinic(
        @Path("token") token: String,
        @Body clinic: Clinic,
    ): Response<Void>

    @PUT("clinic/{token}")
    suspend fun updateClinic(
        @Path("token") token: String,
        @Body clinic: Clinic,
    )

    @GET("clinic/by-doctor")
    suspend fun getClinics(
        @Query("doctorId") token: String,
    ): Response<List<ClinicResponse>>

    @GET("clinic/by-id")
    suspend fun getClinic(
        @Query("clinicId") id: Int,
    ): Clinic

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

    // appointments in day
    @GET("appointment")
    suspend fun getAppointmentsInDay(
        @Query("doctorId") token: String,
        @Query("dateString") dateOfDay: String,
    )
}
