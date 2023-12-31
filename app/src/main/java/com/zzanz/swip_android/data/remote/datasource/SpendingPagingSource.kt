package com.zzanz.swip_android.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.ChallengeService
import com.zzanz.swip_android.data.remote.dto.ChallengeDto
import com.zzanz.swip_android.data.remote.dto.SpendingDto
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpendingPagingSource @Inject constructor(
    private val challengeApi: ChallengeService,
    private val planId: Int
) : PagingSource<Int, SpendingDto>() {
    val planInfo = MutableStateFlow(ChallengeDto.Plan(-1, "", 0, 0))
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpendingDto> {
        return try {
            when (val response = challengeApi.getSpending(planId, params.key, params.loadSize)) {
                is Resource.Success -> {
                    planInfo.emit(ChallengeDto.Plan(
                        planId,
                        response.data.category,
                        response.data.goalAmount,
                        response.data.spendAmount
                    ))
                    LoadResult.Page(
                        data = response.data.spendingList,
                        nextKey = if(response.data.spendingList.isEmpty()) null else response.data.spendingList.last().spendingId,
                        prevKey = null,
                    )
                }

                is Resource.Error -> {
                    LoadResult.Error(response.exception)
                }
            }
        } catch (e: Exception) {
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