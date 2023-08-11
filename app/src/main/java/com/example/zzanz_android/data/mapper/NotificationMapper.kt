package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.NotificationDto
import com.example.zzanz_android.domain.model.NotificationModel

object NotificationMapper : MapperToDto<NotificationDto, NotificationModel> {
    override fun NotificationModel.toDto() = NotificationDto(
        fcmToken = fcmToken,
        operatingSystem = operatingSystem,
        notificationHour = notificationHour,
        notificationMinute = notificationMinute
    )
}