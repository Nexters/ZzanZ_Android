package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.SpendingDto
import com.example.zzanz_android.data.remote.dto.request.SpendingDto as RequestSpendingDto
import com.example.zzanz_android.domain.model.SpendingModel

object SpendingMapper: MapperToModel<SpendingDto, SpendingModel>, MapperToDto<RequestSpendingDto, SpendingModel> {
    override fun SpendingDto.toModel() = SpendingModel(
        id = spendingId,
        title = title,
        memo = memo,
        amount = spendAmount
    )

    override fun SpendingModel.toDto() = RequestSpendingDto(
        title = title,
        memo = memo ?: "",
        spendAmount = amount
    )
}