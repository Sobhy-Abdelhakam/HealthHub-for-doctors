package dev.sobhy.healthhubfordoctors.profilefeature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class DoctorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String?,
    val gender: String,
    val imgPath: String?,
    val specialty: String,
    val profTitle: String,
    val rating: Double,
)
