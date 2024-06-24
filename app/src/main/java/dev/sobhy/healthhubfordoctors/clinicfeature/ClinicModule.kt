package dev.sobhy.healthhubfordoctors.clinicfeature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories.AvailabilityRepositoryImpl
import dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories.ClinicRepositoryImpl
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AddClinicUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AvailabilityUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.GetAllClinicsUseCase
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
object ClinicModule {
    @Provides
    fun provideClinicRepository(
        clinicService: ApiService,
        doctorInfoDao: DoctorInfoDao,
        authPreferencesRepository: AuthPreferencesRepository,
    ): ClinicRepository {
        return ClinicRepositoryImpl(clinicService, doctorInfoDao, authPreferencesRepository)
    }

    @Provides
    fun provideAddClinicUseCase(clinicRepository: ClinicRepository): AddClinicUseCase {
        return AddClinicUseCase(clinicRepository)
    }

    @Provides
    fun provideClinicsUseCase(clinicRepository: ClinicRepository): GetAllClinicsUseCase {
        return GetAllClinicsUseCase(clinicRepository)
    }

    @Provides
    fun provideAvailabilityRepository(apiService: ApiService): AvailabilityRepository {
        return AvailabilityRepositoryImpl(apiService)
    }

    @Provides
    fun provideAddAvailabilityUseCase(availabilityRepository: AvailabilityRepository): AvailabilityUseCase {
        return AvailabilityUseCase(availabilityRepository)
    }
}
