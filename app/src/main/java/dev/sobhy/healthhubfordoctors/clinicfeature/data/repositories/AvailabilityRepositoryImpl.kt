package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AvailabilityRepositoryImpl(private val apiService: ApiService) : AvailabilityRepository {
    override suspend fun setAvailability(availability: Availability) =
        flow {
            emit(Resource.Loading())
            val response = apiService.setAvailability(1, availability)
            emit(Resource.Success("Availability set successfully"))
        }.catch {
            emit(Resource.Error(it.localizedMessage ?: "An error occurred"))
        }
}
