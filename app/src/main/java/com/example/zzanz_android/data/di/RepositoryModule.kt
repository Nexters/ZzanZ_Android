package com.example.zzanz_android.data.di

import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountByCategorySource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.remote.datasource.NotificationSource
import com.example.zzanz_android.data.remote.datasource.SpendingDatasource
import com.example.zzanz_android.data.remote.datasource.prefererence.FcmTokenUserPrefSource
import com.example.zzanz_android.data.remote.datasource.prefererence.LastRoutePrefSource
import com.example.zzanz_android.data.remote.datasource.prefererence.NotificationTimeSource
import com.example.zzanz_android.data.remote.datasource.prefererence.UserPrefSource
import com.example.zzanz_android.data.repository.ChallengeRepositoryImpl
import com.example.zzanz_android.data.repository.NotificationRepositoryImpl
import com.example.zzanz_android.data.repository.SpendingRepositoryImpl
import com.example.zzanz_android.data.repository.UserPreferenceRepositoryImpl
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.repository.NotificationRepository
import com.example.zzanz_android.domain.repository.SpendingRepository
import com.example.zzanz_android.domain.repository.UserPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideChallengeRepository(
        challengePagingSource: ChallengePagingSource,
        goalAmountSource: GoalAmountSource,
        goalAmountByCategorySource: GoalAmountByCategorySource,
    ): ChallengeRepository {
        return ChallengeRepositoryImpl(
            challengePagingSource,
            goalAmountSource,
            goalAmountByCategorySource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideNotificationRepository(
        notificationSource: NotificationSource
    ): NotificationRepository {
        return NotificationRepositoryImpl(
            notificationSource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSpendingRepository(
        challengeService: ChallengeService,
        spendingDatasource: SpendingDatasource
    ): SpendingRepository {
        return SpendingRepositoryImpl(
            challengeService,
            spendingDatasource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUserPreferenceRepository(
        userPrefSource: UserPrefSource,
        fcmTokenUserPrefSource: FcmTokenUserPrefSource,
        lastRoutePrefSource: LastRoutePrefSource,
        notificationTimeSource: NotificationTimeSource
    ): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(
            userPrefSource, fcmTokenUserPrefSource, lastRoutePrefSource, notificationTimeSource
        )
    }
}