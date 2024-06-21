package dev.sobhy.healthhubfordoctors.clinicfeature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories.ClinicRepositoryImpl
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AddClinicUseCase
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService

@Module
@InstallIn(SingletonComponent::class)
object ClinicModule {
    @Provides
    fun provideClinicRepository(clinicService: ApiService): ClinicRepository {
        return ClinicRepositoryImpl(clinicService)
    }

    @Provides
    fun provideAddClinicUseCase(clinicRepository: ClinicRepository): AddClinicUseCase {
        return AddClinicUseCase(clinicRepository)
    }
}
