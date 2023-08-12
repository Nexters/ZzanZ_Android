package com.example.zzanz_android.presentation.view.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SettingUiData(
    val currentRoute: String,
    @StringRes val titleText: Int,
    @StringRes var buttonText: Int,
    val nextRoute: String,
    val backRoute: String,
    @DrawableRes val contentImage: Int? = null,
    @StringRes val explainContent: Int? = null
)
