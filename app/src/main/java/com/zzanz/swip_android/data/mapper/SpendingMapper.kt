package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.SpendingDto
import com.zzanz.swip_android.data.remote.dto.request.SpendingDto as RequestSpendingDto
import com.zzanz.swip_android.domain.model.SpendingModel

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