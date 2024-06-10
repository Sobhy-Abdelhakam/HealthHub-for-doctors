package dev.sobhy.healthhubfordoctors.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.ChangePassUseCase
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.LogoutUseCase

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    fun provideChangePassUseCase(authRepository: AuthRepository): ChangePassUseCase {
        return ChangePassUseCase(authRepository)
    }
}
