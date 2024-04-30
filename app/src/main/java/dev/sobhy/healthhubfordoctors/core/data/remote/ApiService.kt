package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun addDoctor(doctor: DoctorRequest): Flow<Resource<String>>
}
