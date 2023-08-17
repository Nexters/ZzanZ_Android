package com.zzanz.swip_android.data.di

import com.zzanz.swip_android.data.remote.api.ChallengeService
import com.zzanz.swip_android.data.remote.datasource.ChallengePagingSource
import com.zzanz.swip_android.data.remote.datasource.FcmTokenDataSource
import com.zzanz.swip_android.data.remote.datasource.GoalAmountByCategorySource
import com.zzanz.swip_android.data.remote.datasource.GoalAmountSource
import com.zzanz.swip_android.data.remote.datasource.NotificationTimeSource
import com.zzanz.swip_android.data.remote.datasource.SpendingDatasource
import com.zzanz.swip_android.data.remote.datasource.prefererence.FcmTokenUserPrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.LastRoutePrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.NotificationTimeUserPrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.UserPrefSource
import com.zzanz.swip_android.data.repository.ChallengeRepositoryImpl
import com.zzanz.swip_android.data.repository.NotificationRepositoryImpl
import com.zzanz.swip_android.data.repository.SpendingRepositoryImpl
import com.zzanz.swip_android.data.repository.UserPreferenceRepositoryImpl
import com.zzanz.swip_android.domain.repository.ChallengeRepository
import com.zzanz.swip_android.domain.repository.NotificationRepository
import com.zzanz.swip_android.domain.repository.SpendingRepository
import com.zzanz.swip_android.domain.repository.UserPreferenceRepository
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
            challengePagingSource, goalAmountSource, goalAmountByCategorySource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideNotificationRepository(
        notificationTimeSource: NotificationTimeSource, fcmTokenDataSource: FcmTokenDataSource
    ): NotificationRepository {
        return NotificationRepositoryImpl(
            notificationTimeSource, fcmTokenDataSource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSpendingRepository(
        challengeService: ChallengeService, spendingDatasource: SpendingDatasource
    ): SpendingRepository {
        return SpendingRepositoryImpl(
            challengeService, spendingDatasource
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUserPreferenceRepository(
        userPrefSource: UserPrefSource,
        fcmTokenUserPrefSource: FcmTokenUserPrefSource,
        lastRoutePrefSource: LastRoutePrefSource,
        notificationTimeUserPrefSource: NotificationTimeUserPrefSource
    ): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(
            userPrefSource,
            fcmTokenUserPrefSource,
            lastRoutePrefSource,
            notificationTimeUserPrefSource
        )
    }
}