package dev.sobhy.healthhubfordoctors.authfeature.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DoctorRequest(
    val name: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,
    val professionalTitle: String? = null,
    val specialty: String? = null,
)

enum class Gender {
    Male,
    Female,
}
