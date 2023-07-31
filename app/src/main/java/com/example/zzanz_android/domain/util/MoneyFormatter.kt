package com.example.zzanz_android.domain.util

import java.lang.Exception
import java.text.NumberFormat
import java.util.Locale

object MoneyFormatter {
    fun format(target: String): String{
        return try {
            val number = target.toInt()
            NumberFormat.getInstance(Locale.getDefault()).format(number)
        }catch (e: Exception){
            target
        }
    }
}