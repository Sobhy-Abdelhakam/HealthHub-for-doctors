package dev.sobhy.healthhubfordoctors.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorProfile
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctorProfile(doctorProfile: DoctorProfile)

    @Query("SELECT * FROM doctor_profile WHERE uid = :id")
    fun getAllDoctorInfo(id: String): Flow<DoctorProfile?>

    @Query("DELETE FROM doctor_profile WHERE uid = :id")
    suspend fun deleteDoctorInfo(id: String)

    @Query("SELECT name, specialty, imgPath, rating FROM doctor_profile WHERE uid = :id")
    fun getProfileInfo(id: String): Flow<DoctorProfileUiModel?>
}
