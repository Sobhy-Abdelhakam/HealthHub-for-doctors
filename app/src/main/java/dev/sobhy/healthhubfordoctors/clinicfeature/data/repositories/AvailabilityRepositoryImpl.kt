package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.DayState
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.DayOfWeek

class AvailabilityRepositoryImpl(
    private val apiService: ApiService,
    private val doctorInfoDao: DoctorInfoDao,
) : AvailabilityRepository {
    override suspend fun setAvailability(
        availability: Availability,
        clinicId: Int,
    ) = flow {
        emit(Resource.Loading())
        val response = apiService.setAvailability(clinicId, availability.availability)
        // TODO: Save the response to local database
        emit(Resource.Success("Availability set successfully"))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getAvailability(clinicId: Int): Flow<Resource<Availability>> {
        return flow {
            emit(Resource.Loading())
            val response = doctorInfoDao.getAllAvailabilities(clinicId)

            val temp =
                Availability(
                    availability =
                        DayOfWeek.entries.associateWith { day ->
                            val matchingAvailability = response.find { it.day == day.name }
                            if (matchingAvailability != null) {
                                DayState(
                                    status = matchingAvailability.available,
                                    from = matchingAvailability.startTime,
                                    to = matchingAvailability.endTime,
                                )
                            } else {
                                DayState(
                                    status = false,
                                    from = "",
                                    to = "",
                                )
                            }
                        },
                )
            emit(Resource.Success(temp))
        }
    }
}
