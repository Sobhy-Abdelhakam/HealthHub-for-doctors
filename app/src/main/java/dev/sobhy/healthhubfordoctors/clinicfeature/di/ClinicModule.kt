package dev.sobhy.healthhubfordoctors.clinicfeature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.clinicfeature.data.remote.ClinicService
import dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories.AvailabilityRepositoryImpl
import dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories.ClinicRepositoryImpl
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AddClinicUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AvailabilityUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.CurrentAvailabilityUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.GetAllClinicsUseCase
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClinicModule {
    @Provides
    @Singleton
    fun provideClinicService(retrofit: Retrofit): ClinicService {
        return retrofit.create(ClinicService::class.java)
    }

    @Provides
    fun provideClinicRepository(
        clinicService: ClinicService,
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
    fun provideAvailabilityRepository(
        clinicService: ClinicService,
        doctorInfoDao: DoctorInfoDao,
    ): AvailabilityRepository {
        return AvailabilityRepositoryImpl(clinicService, doctorInfoDao)
    }

    @Provides
    fun provideAddAvailabilityUseCase(availabilityRepository: AvailabilityRepository): AvailabilityUseCase {
        return AvailabilityUseCase(availabilityRepository)
    }

    @Provides
    fun provideCurrentAvailabilityUseCase(availabilityRepository: AvailabilityRepository): CurrentAvailabilityUseCase {
        return CurrentAvailabilityUseCase(availabilityRepository)
    }
}
