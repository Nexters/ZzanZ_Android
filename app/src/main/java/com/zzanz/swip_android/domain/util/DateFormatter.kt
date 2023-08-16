package com.zzanz.swip_android.domain.util

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

object DateFormatter {
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    fun format(target: String): String{
        val date = LocalDate.parse(target, format)
        return "${date.monthValue}.${date.dayOfMonth}"
    }

    fun calculateDday(endDate: String): Int{
        val period = Period.between(LocalDate.now(), LocalDate.parse(endDate, format))
        return period.days
    }
}