package dev.sobhy.healthhubfordoctors.profilefeature.domain.model

import dev.sobhy.healthhubfordoctors.core.data.model.DoctorProfile

fun DoctorProfile.toDoctorProfileUiModel(): DoctorProfileUiModel {
    return DoctorProfileUiModel(
        name = this.name,
        specialty = this.specialty,
        imgPath = this.imgPath,
        rating = this.rating,
    )
}
