package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.NotificationTimeDto
import com.example.zzanz_android.domain.model.NotificationTimeModel

object NotificationTimeMapper : MapperToDto<NotificationTimeDto, NotificationTimeModel> {
    override fun NotificationTimeModel.toDto() = NotificationTimeDto(
        notificationHour = notificationHour,
        notificationMinute = notificationMinute
    )
}