package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.UserPrefDto
import com.zzanz.swip_android.domain.model.UserPref

object UserPrefMapper : MapperToDto<UserPrefDto, UserPref>, MapperToModel<UserPrefDto, UserPref> {
    override fun UserPref.toDto() = UserPrefDto(
        route = lastRoute,
        fcmToken = fcmToken,
        notificationMinute = notificationMinute,
        notificationHour = notificationHour
    )

    override fun UserPrefDto.toModel() = UserPref(
        lastRoute = route,
        fcmToken = fcmToken,
        notificationMinute = notificationMinute,
        notificationHour = notificationHour
    )
}