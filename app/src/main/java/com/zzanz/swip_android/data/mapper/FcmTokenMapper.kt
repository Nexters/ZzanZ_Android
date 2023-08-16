package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.FcmTokenDto
import com.zzanz.swip_android.domain.model.FcmTokenModel

object FcmTokenMapper : MapperToDto<FcmTokenDto, FcmTokenModel> {
    override fun FcmTokenModel.toDto() = FcmTokenDto(
        fcmToken = fcmToken,
        operatingSystem = operatingSystem
    )
}