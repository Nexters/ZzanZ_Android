package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.domain.model.BudgetCategoryModel


object GoalAmountByCategoryMapper : MapperToDto<GoalAmountByCategoryDto, BudgetCategoryModel> {
    override fun BudgetCategoryModel.toDto() = GoalAmountByCategoryDto(
        goalAmount = budget.value.toInt(),
        category = categoryId.name
    )

}