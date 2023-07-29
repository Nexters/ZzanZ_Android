package com.example.zzanz_android.common.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimen(
    val defaultHorizontal: Dp = 24.dp
)

val ZzanZDimen = staticCompositionLocalOf { Dimen() }