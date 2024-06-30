package dev.sobhy.healthhubfordoctors.authfeature.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String,
    @SerializedName("username")
    val email: String,
    @SerializedName("phoneNumber")
    val phone: String,
    val gender: String,
    @SerializedName("birthDate")
    val dateOfBirth: String,
    val specialty: Specialty,
    @SerializedName("profTitle")
    val professionalTitle: String,
    val password: String,
)
