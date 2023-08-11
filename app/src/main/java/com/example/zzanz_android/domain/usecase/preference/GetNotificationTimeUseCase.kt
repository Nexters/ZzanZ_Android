package com.example.zzanz_android.domain.usecase.preference

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.GoalAmountMapper.toDto
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import com.example.zzanz_android.domain.model.BudgetModel
import com.example.zzanz_android.domain.model.UserPref
import com.example.zzanz_android.domain.repository.UserPreferenceRepository
import com.example.zzanz_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNotificationTimeUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<List<Int?>, Nothing>() {
    override suspend fun buildRequest(params: Nothing?): Flow<Resource<List<Int?>>> {
        return repository.getNotificationTime().flowOn(dispatcher)
    }
}