package com.example.zzanz_android.domain.usecase.category

import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPlanInfoUseCase @Inject constructor(
    private val planId: Int,
    private val challengeRepository: ChallengeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

}