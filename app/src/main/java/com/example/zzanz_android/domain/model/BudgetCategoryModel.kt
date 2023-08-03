package com.example.zzanz_android.domain.model

import androidx.annotation.StringRes
import com.example.zzanz_android.R

data class BudgetCategoryModel(
    val id: Int,
    @StringRes val name: Int,
    val categoryName: Category,
    var isChecked: Boolean = false,
    var budget: Int = 0
)

object BudgetCategoryData {
    val category = listOf(
        BudgetCategoryModel(
            id = 0,
            name = R.string.category_food,
            categoryName = Category.FOOD,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 1,
            name = R.string.category_eatout,
            categoryName = Category.EATOUT,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 2,
            name = R.string.category_coffee,
            categoryName = Category.COFFEE,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 3,
            name = R.string.category_transportation,
            categoryName = Category.TRANSPORTATION,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 4,
            name = R.string.category_living,
            categoryName = Category.LIVING,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 5,
            name = R.string.category_beauty,
            categoryName = Category.BEAUTY,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 6,
            name = R.string.category_culture,
            categoryName = Category.CULTURE,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 7,
            name = R.string.category_nestegg,
            categoryName = Category.NESTEGG,
            isChecked = false,
            budget = 0
        )
    )
}
