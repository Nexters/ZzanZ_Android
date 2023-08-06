package com.example.zzanz_android.common.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val White: Color = Color(0xFFFFFFFF),
    val Black: Color = Color(0xFF000000),

    val Green09: Color = Color(0xFF19241F),
    val Green08: Color = Color(0xFF172E22),
    val Green07: Color = Color(0xFF214D35),
    val Green06: Color = Color(0xFF1D663E),
    val Green05: Color = Color(0xFF18A155),
    val Green04: Color = Color(0xFF1AB662),
    val Green03: Color = Color(0xFF73BF82),
    val Green02: Color = Color(0xFFA4D3A8),
    val Green01: Color = Color(0xFFD0F0D5),
    val GreenCategory : Color = Color(0xFFE9FFF3),

    val Gray09: Color = Color(0xFF141415),
    val Gray08: Color = Color(0xFF222529),
    val Gray07: Color = Color(0xFF333D4B),
    val Gray06: Color = Color(0xFF505B6A),
    val Gray05: Color = Color(0xFF8A939D),
    val Gray04: Color = Color(0xFFB3BDC9),
    val Gray03: Color = Color(0xFFD6DCE2),
    val Gray02: Color = Color(0xFFE4E7EA),
    val Gray01: Color = Color(0xFFF1F3F5),

    val Red09: Color = Color(0xFF251313),
    val Red08: Color = Color(0xFF301414),
    val Red07: Color = Color(0xFF622020),
    val Red06: Color = Color(0xFF942B2B),
    val Red05: Color = Color(0xFFC63737),
    val Red04: Color = Color(0xFFF84242),
    val Red03: Color = Color(0xFFF97C7C),
    val Red02: Color = Color(0xFFFBB6B6),
    val Red01: Color = Color(0xFFFCF0F0),
)

val ZzanZColorPalette = staticCompositionLocalOf { ColorPalette() }