package com.example.zzanz_android.domain.util

import android.content.Context
import com.example.zzanz_android.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.NumberFormat
import java.util.Locale

object MoneyFormatter {
    fun format(target: String) = NumberFormat.getInstance(Locale.getDefault()).format(target.toInt())
}