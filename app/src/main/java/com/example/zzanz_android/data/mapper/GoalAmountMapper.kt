package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.domain.model.BudgetModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

object GoalAmountMapper : MapperToDto<GoalAmountDto, BudgetModel> {
    override fun BudgetModel.toDto() = GoalAmountDto(
        goalAmount = budget
    )
}