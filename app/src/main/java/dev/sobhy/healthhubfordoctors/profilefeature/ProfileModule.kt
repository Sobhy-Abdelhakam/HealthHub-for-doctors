package dev.sobhy.healthhubfordoctors.profilefeature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.profilefeature.data.repository.ProfileRepositoryImpl
import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.ChangePassUseCase
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.LogoutUseCase
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.ProfileInfoUseCase

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    fun provideProfileRepository(
        doctorInfoDao: DoctorInfoDao,
        apiService: ApiService,
        authPreferencesRepository: AuthPreferencesRepository,
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            doctorInfoDao,
            apiService,
            authPreferencesRepository,
        )
    }

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    fun provideChangePassUseCase(authRepository: AuthRepository): ChangePassUseCase {
        return ChangePassUseCase(authRepository)
    }

    @Provides
    fun provideProfileInfoUseCase(profileInfoRepository: ProfileRepository): ProfileInfoUseCase  {
        return ProfileInfoUseCase(profileInfoRepository)
    }
}
