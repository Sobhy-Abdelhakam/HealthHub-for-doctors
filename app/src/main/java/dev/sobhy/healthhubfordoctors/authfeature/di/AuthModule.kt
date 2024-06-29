package dev.sobhy.healthhubfordoctors.authfeature.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSourceImpl
import dev.sobhy.healthhubfordoctors.authfeature.data.repository.AuthRepositoryImpl
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ForgetPasswordUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.LoginUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.RegisterUseCase
import dev.sobhy.healthhubfordoctors.core.data.remote.DoctorService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    @Provides
    fun provideAuthRepository(
        authDataSource: FirebaseAuthDataSource,
        doctorService: DoctorService,
        authPreferences: AuthPreferencesRepository,
    ): AuthRepository {
        return AuthRepositoryImpl(authDataSource, doctorService, authPreferences)
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
