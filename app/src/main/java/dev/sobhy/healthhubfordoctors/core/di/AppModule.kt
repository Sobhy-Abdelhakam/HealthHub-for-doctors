package dev.sobhy.healthhubfordoctors.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.core.datasource.AuthPreferencesState
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthPrefState(
        @ApplicationContext context: Context,
    ): AuthPreferencesState {
        return AuthPreferencesState(context)
    }

    @Provides
    @Singleton
    fun provideAuthPrefRepo(authPrefState: AuthPreferencesState): AuthPreferencesRepository {
        return AuthPreferencesRepositoryImpl(authPrefState)
    }
}
