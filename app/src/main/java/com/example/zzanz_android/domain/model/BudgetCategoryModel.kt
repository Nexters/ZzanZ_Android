package com.example.zzanz_android.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.zzanz_android.R

data class BudgetCategoryModel(
    val id: Int,
    @StringRes val name: Int,
    val categoryId: Category,
    var isChecked: Boolean = false,
    var budget: Int = 0,
    @DrawableRes val categoryImage: Int? = null
)

object BudgetCategoryData {
    val category = listOf(
        BudgetCategoryModel(
            id = 0,
            name = R.string.category_food,
            categoryId = Category.FOOD,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 1,
            name = R.string.category_eatout,
            categoryId = Category.EATOUT,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 2,
            name = R.string.category_coffee,
            categoryId = Category.COFFEE,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 3,
            name = R.string.category_transportation,
            categoryId = Category.TRANSPORTATION,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 4,
            name = R.string.category_living,
            categoryId = Category.LIVING,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 5,
            name = R.string.category_beauty,
            categoryId = Category.BEAUTY,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 6,
            name = R.string.category_culture,
            categoryId = Category.CULTURE,
            isChecked = false,
            budget = 0
        ), BudgetCategoryModel(
            id = 7,
            name = R.string.category_nestegg,
            categoryId = Category.NESTEGG,
            isChecked = true,
            budget = 0
        )
    )
}
