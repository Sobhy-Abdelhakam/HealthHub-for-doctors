package dev.sobhy.healthhubfordoctors.authfeature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FireStoreDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FireStoreDataSourceImpl
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSource
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.FirebaseAuthDataSourceImpl
import dev.sobhy.healthhubfordoctors.authfeature.data.repository.AuthRepositoryImpl
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.ForgetPasswordUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.LoginUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.RegisterUseCase
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
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
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreDataSource(firebaseFireStore: FirebaseFirestore): FireStoreDataSource {
        return FireStoreDataSourceImpl(firebaseFireStore)
    }

    @Provides
    fun provideAuthRepository(
        authDataSource: FirebaseAuthDataSource,
        apiService: ApiService,
        authPreferences: AuthPreferencesRepository,
    ): AuthRepository {
        return AuthRepositoryImpl(authDataSource, apiService, authPreferences)
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
