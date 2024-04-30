package dev.sobhy.healthhubfordoctors.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(DefaultRequest) {
                url("http://127.0.0.1:8080/")
            }
        }
    }

    fun provideApiService(httpClient: HttpClient): ApiService {
        return ApiServiceImpl(httpClient)
    }
}
