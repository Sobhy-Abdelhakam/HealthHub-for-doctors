package dev.sobhy.healthhubfordoctors.authfeature.domain.model

data class DoctorRequest(
    val uid: String,
    val name: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val dateOfBirth: String? = null,
    val gender: Gender? = null,
    val professionalTitle: String? = null,
    val specialty: String? = null,
)

enum class Gender {
    Male,
    Female,
}
