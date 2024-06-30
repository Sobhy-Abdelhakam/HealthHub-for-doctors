package dev.sobhy.healthhubfordoctors.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicWithAvailabilities
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctorProfile(doctor: DoctorEntity)

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getAllDoctorInfo(id: Int): Flow<DoctorEntity?>

    @Update
    fun updateDoctor(doctor: DoctorEntity)

    @Query("SELECT * FROM clinics WHERE doctorId = :doctorId")
    fun getClinicsForDoctor(doctorId: String): Flow<List<ClinicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClinic(clinic: ClinicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClinics(clinics: List<ClinicEntity>)

    @Update
    suspend fun updateClinic(clinic: ClinicEntity)

    @Query("SELECT * FROM availabilities WHERE clinicId = :clinicId")
    fun getAvailabilityForClinic(clinicId: Int): Flow<List<AvailabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAvailability(availability: AvailabilityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAvailabilities(availabilities: List<AvailabilityEntity>)

    @Update
    suspend fun updateAvailability(availability: AvailabilityEntity)

    @Query("SELECT * FROM availabilities where clinicId = :clinicId")
    fun getAllAvailabilities(clinicId: Int): List<AvailabilityEntity>

    @Transaction
    @Query("SELECT * FROM clinics WHERE doctorId = :doctorId")
    fun getClinicsWithAvailabilities(doctorId: Int): Flow<List<ClinicWithAvailabilities>>
}
