package dev.sobhy.healthhubfordoctors.core

import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorResponse
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity

data class Doctor(
    val uid: Int,
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

data class Clinic(
    val id: Int,
    val doctorId: Int,
    val name: String,
    val phone: String?,
    val examination: Double,
    val followUp: Double,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val availabilities: List<Availability>,
)

data class Availability(
    val id: Int,
    val clinicId: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
)

fun ClinicEntity.toClinic(availabilities: List<Availability>): Clinic {
    return Clinic(
        id = id,
        doctorId = doctorId,
        name = name,
        phone = phone,
        examination = examination,
        followUp = followUp,
        latitude = latitude,
        longitude = longitude,
        address = address,
        availabilities = availabilities,
    )
}

fun AvailabilityEntity.toAvailability(): Availability {
    return Availability(
        id = id,
        clinicId = clinicId,
        day = day,
        startTime = startTime,
        endTime = endTime,
        available = available,
    )
}

fun DoctorEntity.toDoctor() = Doctor(id, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)

fun Doctor.toEntity() = DoctorEntity(uid, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)

fun DoctorResponse.toDoctor() = Doctor(id, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)
