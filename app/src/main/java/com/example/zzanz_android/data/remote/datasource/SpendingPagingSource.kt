package com.example.zzanz_android.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.dto.SpendingDto
import javax.inject.Inject

class SpendingPagingSource @Inject constructor(
    private val planId: Int,
    private val challengeApi: ChallengeService
): PagingSource<Int, SpendingDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpendingDto> {
        return try {
            when(val response = challengeApi.getSpending(planId, params.key, params.loadSize)){
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data.spendingList,
                        nextKey = response.data.spendingList.last().spendingId,
                        prevKey = null,
                    )
                }
                is Resource.Error -> {
                    LoadResult.Error(response.exception)
                }
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SpendingDto>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}