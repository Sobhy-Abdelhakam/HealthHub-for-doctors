package dev.sobhy.healthhubfordoctors.profilefeature

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.profilefeature.data.local.DoctorProfileDao
import dev.sobhy.healthhubfordoctors.profilefeature.data.local.ProfileDatabase
import dev.sobhy.healthhubfordoctors.profilefeature.data.repository.ProfileRepositoryImpl
import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.GetDoctorProfileUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ProfileDatabase {
        return Room.databaseBuilder(
            context,
            ProfileDatabase::class.java,
            "profile_database",
        ).build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(database: ProfileDatabase): DoctorProfileDao {
        return database.doctorProfileDao()
    }

    @Provides
    fun provideProfileRepository(
        apiService: ApiService,
        profileDao: DoctorProfileDao,
        authPreferences: AuthPreferencesRepository,
    ): ProfileRepository {
        return ProfileRepositoryImpl(apiService, profileDao, authPreferences)
    }

    @Provides
    fun provideProfileUseCase(profileRepository: ProfileRepository): GetDoctorProfileUseCase {
        return GetDoctorProfileUseCase(profileRepository)
    }
}
