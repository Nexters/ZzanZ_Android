package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.SpendingDto
import com.example.zzanz_android.domain.model.SpendingModel

object SpendingMapper: MapperToModel<SpendingDto, SpendingModel> {
    override fun SpendingDto.toModel() = SpendingModel(
        id = spendingId,
        title = title,
        memo = memo,
        amount = spendAmount
    )
}