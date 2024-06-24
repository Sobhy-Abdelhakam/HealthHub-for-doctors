package dev.sobhy.healthhubfordoctors.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sobhy.healthhubfordoctors.core.data.model.Converters
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorProfile

@Database(entities = [DoctorProfile::class], version = 1)
@TypeConverters(Converters::class)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun doctorProfileDao(): DoctorInfoDao
}
