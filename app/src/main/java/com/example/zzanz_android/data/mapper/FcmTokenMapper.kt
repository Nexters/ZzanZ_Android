package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.FcmTokenDto
import com.example.zzanz_android.domain.model.FcmTokenModel
import com.example.zzanz_android.domain.model.NotificationTimeModel

object FcmTokenMapper : MapperToDto<FcmTokenDto, FcmTokenModel> {
    override fun FcmTokenModel.toDto() = FcmTokenDto(
        fcmToken = fcmToken,
        operatingSystem = operatingSystem
    )
}