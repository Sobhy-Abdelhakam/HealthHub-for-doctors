package dev.sobhy.healthhubfordoctors.authfeature.data.models

data class UserDetailsModel(
    val id: String,
    val name: String,
    val image: String? = null,
    val email: String,
    val phone: String,
    val gender: String,
    val dateOfBirth: String,
    val specialization: String,
    val professionalTitle: String?,
    val rate: Int? = 0,
    val createdAt: Long,
)
