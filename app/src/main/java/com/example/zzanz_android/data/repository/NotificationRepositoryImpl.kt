package com.example.zzanz_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.ChallengeMapper.toModel
import com.example.zzanz_android.data.mapper.SpendingMapper.toModel
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountByCategorySource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.remote.datasource.NotificationSource
import com.example.zzanz_android.data.remote.datasource.SpendingPagingSource
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.data.remote.dto.NotificationDto
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(
    private val notificationSource: NotificationSource,
) : NotificationRepository {
    override suspend fun postNotificationConfig(notificationDto: NotificationDto): Flow<Resource<Boolean>> {
        return flow {
            val result = notificationSource.load(notificationDto = notificationDto)
            emit(result)
        }
    }

}