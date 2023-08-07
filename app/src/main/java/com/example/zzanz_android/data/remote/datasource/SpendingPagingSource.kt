package com.example.zzanz_android.data.remote.datasource

import androidx.annotation.IntegerRes
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.dto.SpendingDto
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class SpendingPagingSource @Inject constructor(
    @Named("planId")
    private val planId: Int,
    private val challengeApi: ChallengeServiceImpl
) : PagingSource<Int, SpendingDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpendingDto> {
        return try {
            when (val response = challengeApi.getSpending(planId, params.key, params.loadSize)) {
                is Resource.Loading -> {
                    // TODO - Resource에 Loading도 필요 할거 같아서 클래스에 추가하여,
                    //  아래 코드도 유나 임시로 추가해둠
                    LoadResult.Invalid()
                }

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