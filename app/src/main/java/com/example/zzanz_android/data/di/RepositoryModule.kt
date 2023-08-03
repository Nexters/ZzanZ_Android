package com.example.zzanz_android.data.di

import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountByCategorySource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.repository.ChallengeRepositoryImpl
import com.example.zzanz_android.domain.repository.ChallengeRepository
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
        goalAmountByCategorySource: GoalAmountByCategorySource
    ) : ChallengeRepository {
        return ChallengeRepositoryImpl(challengePagingSource, goalAmountSource, goalAmountByCategorySource)
    }
}