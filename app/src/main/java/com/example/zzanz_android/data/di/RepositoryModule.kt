package com.example.zzanz_android.data.di

import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountByCategorySource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.remote.datasource.SpendingPagingSource
import com.example.zzanz_android.data.repository.ChallengeRepositoryImpl
import com.example.zzanz_android.data.repository.SpendingRepositoryImpl
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.repository.SpendingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideChallengeRepository(
        challengePagingSource: ChallengePagingSource,
        goalAmountSource: GoalAmountSource,
        goalAmountByCategorySource: GoalAmountByCategorySource,
        spendingPagingSource: SpendingPagingSource
    ): ChallengeRepository {
        return ChallengeRepositoryImpl(
            challengePagingSource,
            goalAmountSource,
            goalAmountByCategorySource,
            spendingPagingSource
        )
    }

    @Provides
    @Singleton
    fun provideSpendingRepository(
        challengeService: ChallengeService
    ): SpendingRepository {
        return SpendingRepositoryImpl(challengeService)
    }
}