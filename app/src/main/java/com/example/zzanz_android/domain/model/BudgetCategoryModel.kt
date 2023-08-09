package com.example.zzanz_android.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.zzanz_android.R

data class BudgetCategoryModel(
    val id: Int,
    @StringRes val name: Int,
    val categoryId: Category,
    var isChecked: Boolean = false,
    var budget: String = "",
    @DrawableRes val categoryImage: Int
)

object BudgetCategoryData {
    var totalBudget: MutableState<String> = mutableStateOf("")
    val category = mutableStateOf(
        listOf(
            BudgetCategoryModel(
                id = 0,
                name = R.string.category_food,
                categoryId = Category.FOOD,
                isChecked = false,
                categoryImage = R.drawable.icon_food
            ), BudgetCategoryModel(
                id = 1,
                name = R.string.category_eatout,
                categoryId = Category.EATOUT,
                isChecked = false,
                categoryImage = R.drawable.icon_eatout
            ), BudgetCategoryModel(
                id = 2,
                name = R.string.category_coffee,
                categoryId = Category.COFFEE,
                isChecked = false,
                categoryImage = R.drawable.icon_coffee
            ), BudgetCategoryModel(
                id = 3,
                name = R.string.category_transportation,
                categoryId = Category.TRANSPORTATION,
                isChecked = false,
                categoryImage = R.drawable.icon_transporamtion
            ), BudgetCategoryModel(
                id = 4,
                name = R.string.category_living,
                categoryId = Category.LIVING,
                isChecked = false,
                categoryImage = R.drawable.icon_living
            ), BudgetCategoryModel(
                id = 5,
                name = R.string.category_beauty,
                categoryId = Category.BEAUTY,
                isChecked = false,
                categoryImage = R.drawable.icon_beauty
            ), BudgetCategoryModel(
                id = 6,
                name = R.string.category_culture,
                categoryId = Category.CULTURE,
                isChecked = false,
                categoryImage = R.drawable.icon_culture
            ), BudgetCategoryModel(
                id = 7,
                name = R.string.category_nestegg,
                budget = "0",
                categoryId = Category.NESTEGG,
                isChecked = true,
                categoryImage = R.drawable.icon_nestegg
            )
        )
    )
}
