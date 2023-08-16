package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto
import com.zzanz.swip_android.domain.model.NotificationTimeModel

object NotificationTimeMapper : MapperToDto<NotificationTimeDto, NotificationTimeModel> {
    override fun NotificationTimeModel.toDto() = NotificationTimeDto(
        notificationHour = notificationHour,
        notificationMinute = notificationMinute
    )
}