package dev.sobhy.healthhubfordoctors.clinicfeature.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity

@Entity(
    tableName = "clinics",
    foreignKeys = [
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = ["uid"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("doctorId")],
)
data class ClinicEntity(
    @PrimaryKey val id: Int,
    val doctorId: String,
    val name: String,
    val phone: String,
    val examination: Double,
    val followUp: Double,
    val latitude: Double,
    val longitude: Double,
    val address: String,
)
