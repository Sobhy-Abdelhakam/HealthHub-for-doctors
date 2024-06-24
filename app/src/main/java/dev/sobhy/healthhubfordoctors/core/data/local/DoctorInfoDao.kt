package dev.sobhy.healthhubfordoctors.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorProfile

@Dao
interface DoctorInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctorProfile(doctorProfile: DoctorProfile)

    @Query("SELECT * FROM doctor_profile WHERE uid = :id")
    suspend fun getDoctorProfile(id: String): DoctorProfile?
}
