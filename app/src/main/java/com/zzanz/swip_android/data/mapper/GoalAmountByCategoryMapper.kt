package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.GoalAmountByCategoryDto
import com.zzanz.swip_android.domain.model.BudgetCategoryModel


object GoalAmountByCategoryMapper : MapperToDto<GoalAmountByCategoryDto, BudgetCategoryModel> {
    override fun BudgetCategoryModel.toDto() = GoalAmountByCategoryDto(
        goalAmount = budget.toInt(),
        category = categoryId.name
    )

}