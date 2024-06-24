package dev.sobhy.healthhubfordoctors.clinicfeature.data.model

data class ClinicRequest(
    val name: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val examination: String,
    val followUp: String,
)
