package dev.sobhy.healthhubfordoctors.authfeature.domain.model

data class DoctorRequest(
    val id: String,
    val name: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: String? = null,
    val gender: Gender? = null,
    val profTitle: String? = null,
    val specialty: Specialty? = null,
)

data class Specialty(
    val name: String,
)

enum class Gender {
    Male,
    Female,
}
