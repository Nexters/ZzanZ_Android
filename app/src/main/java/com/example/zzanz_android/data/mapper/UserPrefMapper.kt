package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.UserPrefDto
import com.example.zzanz_android.domain.model.UserPref

object UserPrefMapper : MapperToDto<UserPrefDto, UserPref>, MapperToModel<UserPrefDto, UserPref> {
    override fun UserPref.toDto() = UserPrefDto(
        route = lastRoute, fcmToken = fcmToken
    )

    override fun UserPrefDto.toModel() = UserPref(
        lastRoute = route, fcmToken = fcmToken
    )
}