package dev.sobhy.healthhubfordoctors.core

import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.AvailabilityResponse
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.ClinicResponse
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.core.data.model.DoctorResponse
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity

data class Doctor(
    val uid: String,
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val gender: String,
    val imgPath: String?,
    val specialty: String,
    val profTitle: String,
    val rating: Double,
)

data class Clinic(
    val id: Int,
    val doctorId: String,
    val name: String,
    val phone: String?,
    val examination: Double,
    val followUp: Double,
    val latitude: Double,
    val longitude: Double,
    val address: String,
)

data class Availability(
    val id: Int,
    val clinicId: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
)

fun DoctorEntity.toDoctor() = Doctor(uid, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)

fun Doctor.toEntity() = DoctorEntity(uid, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)

fun ClinicEntity.toClinic() = Clinic(id, doctorId, name, phone, examination, followUp, latitude, longitude, address)

fun Clinic.toEntity() = ClinicEntity(id, doctorId, name, phone, examination, followUp, latitude, longitude, address)

fun AvailabilityEntity.toAvailability() = Availability(id, clinicId, day, startTime, endTime, available)

fun Availability.toEntity() = AvailabilityEntity(id, clinicId, day, startTime, endTime, available)

fun DoctorResponse.toDoctor() = Doctor(uid, name, birthDate, phoneNumber, email, gender, imgPath, specialty, profTitle, rating)

fun ClinicResponse.toClinic(doctorId: String) =
    Clinic(
        id = id,
        doctorId = doctorId,
        name = name,
        phone = phone,
        examination = examination,
        followUp = followUp,
        latitude = latitude,
        longitude = longitude,
        address = address,
    )

fun AvailabilityResponse.toAvailability(clinicId: Int) = Availability(id, clinicId, day, startTime, endTime, available)
