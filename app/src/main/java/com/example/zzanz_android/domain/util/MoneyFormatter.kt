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

    fun format(target: Int): String{
        return try {
            NumberFormat.getInstance(Locale.getDefault()).format(target)
        }catch (e: Exception){
            target.toString()
        }
    }
}