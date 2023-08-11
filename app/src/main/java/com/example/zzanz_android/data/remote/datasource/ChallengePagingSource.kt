package com.example.zzanz_android.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl_Factory
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import javax.inject.Inject
import kotlin.Exception

class ChallengePagingSource @Inject constructor(
    private val challengeApi: ChallengeServiceImpl
): PagingSource<Int, ChallengeDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChallengeDto> {
        return try {
            when(val response = challengeApi.getChallengeParticipate(params.key, params.loadSize)){
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data,
                        nextKey = response.data.last().challengeId,
                        prevKey = null
                    )
                }
                is Resource.Error -> {
                    throw Exception(response.exception)
                }
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChallengeDto>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}