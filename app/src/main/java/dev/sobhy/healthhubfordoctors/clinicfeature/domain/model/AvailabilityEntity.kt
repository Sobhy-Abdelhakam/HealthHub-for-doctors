package dev.sobhy.healthhubfordoctors.clinicfeature.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "availabilities",
    foreignKeys = [
        ForeignKey(
            entity = ClinicEntity::class,
            parentColumns = ["id"],
            childColumns = ["clinicId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("clinicId")],
)
data class AvailabilityEntity(
    @PrimaryKey val id: Int,
    val clinicId: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
)
