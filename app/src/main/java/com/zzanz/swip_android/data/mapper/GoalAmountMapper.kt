package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.GoalAmountDto
import com.zzanz.swip_android.domain.model.BudgetModel

object GoalAmountMapper : MapperToDto<GoalAmountDto, BudgetModel> {
    override fun BudgetModel.toDto() = GoalAmountDto(
        goalAmount = budget
    )
}