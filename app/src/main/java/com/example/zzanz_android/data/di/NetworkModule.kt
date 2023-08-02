package com.example.zzanz_android.data.di

import android.content.Context
import com.example.zzanz_android.BuildConfig
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.repository.ChallengeRepositoryImpl
import com.example.zzanz_android.data.util.AuthorizationManager
import com.example.zzanz_android.domain.repository.ChallengeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val apiVersion = "/v1"
    @Singleton
    @Provides
    fun provideKtorClient(@ApplicationContext applicationContext: Context): HttpClient {
        return HttpClient(CIO){
            defaultRequest {
                url(BuildConfig.BASE_URL + apiVersion)
                header("Authorization", AuthorizationManager.getDeviceId(applicationContext))
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}