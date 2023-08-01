package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.domain.model.BudgetModel

object GoalAmountMapper : MapperToDto<GoalAmountDto, BudgetModel> {
    override fun BudgetModel.toDto() = GoalAmountDto(
        goalAmount = budget
    )
}