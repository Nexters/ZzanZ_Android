package com.example.zzanz_android.data.di

import android.content.Context
import android.util.Log
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
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val apiVersion = "/v1"
    private const val appVersion = "/0.5"

    @Singleton
    @Provides
    fun provideKtorClient(@ApplicationContext applicationContext: Context): HttpClient {
        return HttpClient(CIO){
            install(Logging){
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            defaultRequest {
                // TODO - apiVersion이 url에 붙지 않음
                url(BuildConfig.BASE_URL + apiVersion)
                header("Authorization", AuthorizationManager.getDeviceId(applicationContext))
                header("App-Version", "Android$appVersion")
                Log.d("###", this.url.toString() )
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }

        }
    }
}