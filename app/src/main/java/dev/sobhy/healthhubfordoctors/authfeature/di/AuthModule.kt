package dev.sobhy.healthhubfordoctors.authfeature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.AuthService
import dev.sobhy.healthhubfordoctors.authfeature.data.repository.AuthRepositoryImpl
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ForgetPasswordUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.LoginUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.RegisterUseCase
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideAuthRepository(
        authService: AuthService,
        authPreferences: AuthPreferencesRepository,
    ): AuthRepository {
        return AuthRepositoryImpl(authService, authPreferences)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    fun provideFPassUseCase(authRepository: AuthRepository): ForgetPasswordUseCase {
        return ForgetPasswordUseCase(authRepository)
    }
}
