package com.example.zzanz_android.domain.usecase.preference

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.UserPrefMapper.toDto
import com.example.zzanz_android.domain.model.UserPref
import com.example.zzanz_android.domain.repository.UserPreferenceRepository
import com.example.zzanz_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SetUserPrefUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, UserPref>() {
    override suspend fun buildRequest(params: UserPref?): Flow<Resource<Boolean>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("UserPref can not be null")))
            }.flowOn(dispatcher)
        }
        return repository.setUserPref(userPref = params.toDto())
    }
}