package com.example.zzanz_android.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zzanz_android.data.remote.api.ChallengeApi
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import java.lang.Exception
import javax.inject.Inject

class ChallengePagingSourceImpl @Inject constructor(
    private val challengeApi: ChallengeApi
): PagingSource<Int, ChallengeDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChallengeDto> {
        return try {
            val response = challengeApi.getChallengeParticipate(params.key, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = response.last().challengeId
            )
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