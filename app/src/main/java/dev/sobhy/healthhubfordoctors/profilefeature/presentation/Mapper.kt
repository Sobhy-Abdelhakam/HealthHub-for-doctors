package dev.sobhy.healthhubfordoctors.profilefeature.presentation

import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfile
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel

fun DoctorProfile.mapToUiModel(): DoctorProfileUiModel {
    return DoctorProfileUiModel(
        name = this.name,
        specialization = this.specialty,
        profilePicture = this.imgPath,
        rating = this.rating,
    )
}
