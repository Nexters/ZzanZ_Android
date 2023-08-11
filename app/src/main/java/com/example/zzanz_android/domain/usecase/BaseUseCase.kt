package com.example.zzanz_android.domain.usecase

import androidx.annotation.Nullable
import com.example.zzanz_android.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<Model, Params> {

    abstract suspend fun buildRequest(params: Params?): Flow<Resource<Model>>

    suspend operator fun invoke(params: Params?): Flow<Resource<Model>> {
        return try {
            buildRequest(params)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }
}