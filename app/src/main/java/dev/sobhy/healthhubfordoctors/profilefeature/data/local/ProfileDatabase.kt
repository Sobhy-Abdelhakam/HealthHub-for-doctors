package dev.sobhy.healthhubfordoctors.profilefeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.Converters
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfile

@Database(entities = [DoctorProfile::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun doctorProfileDao(): DoctorProfileDao
}
