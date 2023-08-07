package com.example.zzanz_android.data.di

import android.content.Context
import com.example.zzanz_android.BuildConfig
import com.example.zzanz_android.data.util.AppVersionUtil
import com.example.zzanz_android.data.util.AuthorizationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val apiVersion = "v1/"

    @Singleton
    @Provides
    fun provideKtorClient(@ApplicationContext applicationContext: Context): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = BuildConfig.BASE_URL
                    port = 8080
                    path(apiVersion)
                }
                header("Authorization", AuthorizationManager.getDeviceId(applicationContext))
                header("App-Version", AppVersionUtil.getVersionHeader())
            }
            // TODO - Logging message Not Working
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        Timber.d("Ktor Logger", message)
//                    }
//                }
//                level = LogLevel.ALL
//            }
            install(HttpTimeout) {
                // TODO - 타임아웃 지정해야 함
                requestTimeoutMillis = 3000
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