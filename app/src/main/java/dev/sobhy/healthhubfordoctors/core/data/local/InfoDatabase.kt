package dev.sobhy.healthhubfordoctors.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity

@Database(
    entities = [
        DoctorEntity::class,
        ClinicEntity::class,
        AvailabilityEntity::class,
    ],
    version = 1,
)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun doctorProfileDao(): DoctorInfoDao
}
