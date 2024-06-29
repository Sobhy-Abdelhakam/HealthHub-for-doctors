package dev.sobhy.healthhubfordoctors.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.local.InfoDatabase
import dev.sobhy.healthhubfordoctors.core.data.remote.DoctorService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.repository.CacheDoctorInfoRepository
import dev.sobhy.healthhubfordoctors.schedulefeature.domain.CacheInfoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): InfoDatabase {
        return Room.databaseBuilder(
            context,
            InfoDatabase::class.java,
            "profile_database",
        ).allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(database: InfoDatabase): DoctorInfoDao {
        return database.doctorProfileDao()
    }

    @Provides
    fun provideProfileRepository(
        doctorService: DoctorService,
        profileDao: DoctorInfoDao,
        authPreferences: AuthPreferencesRepository,
    ): CacheDoctorInfoRepository {
        return CacheDoctorInfoRepository(doctorService, profileDao, authPreferences)
    }

    @Provides
    fun provideProfileUseCase(cacheDoctorInfoRepository: CacheDoctorInfoRepository): CacheInfoUseCase {
        return CacheInfoUseCase(cacheDoctorInfoRepository)
    }
}
