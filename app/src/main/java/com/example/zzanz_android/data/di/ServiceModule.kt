package com.example.zzanz_android.data.di

import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.api.NotificationService
import com.example.zzanz_android.data.remote.api.NotificationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideChallengeService(client: HttpClient): ChallengeService {
        return ChallengeServiceImpl(client)
    }

    @Provides
    fun provideNotificationService(client: HttpClient): NotificationService {
        return NotificationServiceImpl(client)
    }
}