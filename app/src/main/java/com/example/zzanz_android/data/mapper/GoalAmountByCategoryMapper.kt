package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


object GoalAmountByCategoryMapper : MapperToDto<GoalAmountByCategoryDto, BudgetCategoryModel> {
    override fun BudgetCategoryModel.toDto() = GoalAmountByCategoryDto(
        goalAmount = budget,
        category = categoryName.name
    )

}