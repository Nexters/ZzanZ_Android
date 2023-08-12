package com.example.zzanz_android.domain.model

import com.example.zzanz_android.R

enum class Category(val stringResId: Int, val imgResId: Int) {
    FOOD(R.string.category_food, R.drawable.icon_food),
    EATOUT(R.string.category_eatout, R.drawable.icon_eatout),
    COFFEE(R.string.category_coffee, R.drawable.icon_coffee),
    TRANSPORTATION(R.string.category_transportation, R.drawable.icon_transporamtion),
    LIVING(R.string.category_living, R.drawable.icon_living),
    BEAUTY(R.string.category_beauty, R.drawable.icon_beauty),
    CULTURE(R.string.category_culture, R.drawable.icon_culture),
    NESTEGG(R.string.category_nestegg, R.drawable.icon_nestegg)
}