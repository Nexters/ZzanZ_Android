package com.zzanz.swip_android.domain.util

import java.text.NumberFormat
import java.util.Locale
import kotlin.Exception

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

    fun revert(target: String, unit: String? = null): Int{
        return try {
            var result = target.replace(",", "")
            unit?.let {
                result = result.replace(it, "")
            }
            if(result.isEmpty()) 0 else result.toInt()
        }catch (e: Exception){
            -1
        }
    }
}