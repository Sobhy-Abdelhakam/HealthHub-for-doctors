package dev.sobhy.healthhubfordoctors.clinicfeature.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class ClinicWithAvailabilities(
    @Embedded val clinic: ClinicEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "clinicId",
    )
    val availabilities: List<AvailabilityEntity>,
)
