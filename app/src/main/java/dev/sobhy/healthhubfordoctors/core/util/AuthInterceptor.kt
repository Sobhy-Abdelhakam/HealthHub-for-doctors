package dev.sobhy.healthhubfordoctors.core.util

import dev.sobhy.healthhubfordoctors.core.datasource.AuthPreferencesState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

// AuthInterceptor using AuthPreferencesState
@Singleton
class AuthInterceptor
    @Inject
    constructor(
        private val authPreferencesState: AuthPreferencesState,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val token: String? =
                runBlocking {
                    authPreferencesState.userToken.firstOrNull()
                }

            if (token.isNullOrEmpty()) {
                return chain.proceed(originalRequest)
            }

            val newRequest =
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()

            return chain.proceed(newRequest)
        }
    }
