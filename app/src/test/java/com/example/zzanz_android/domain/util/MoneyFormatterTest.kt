package com.example.zzanz_android.domain.util

import org.junit.Assert.*

import org.junit.Test

class MoneyFormatterTest {

    @Test
    fun format() {
        val result = MoneyFormatter.format("1000000") // MAX VALUE ( 최대 백만원대 )
        assertEquals("1,000,000", result)
    }
}