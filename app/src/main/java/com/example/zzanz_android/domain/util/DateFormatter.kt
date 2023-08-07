package com.example.zzanz_android.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun format(target: String): String{
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(target, format)
        return "${date.monthValue}.${date.dayOfMonth}"
    }
}