package com.example.zzanz_android.domain.util

import org.junit.Assert.*

import org.junit.Test

class MoneyFormatterTest {

    @Test
    fun format() {
        val result = MoneyFormatter.format("1000000") // MAX VALUE ( 최대 백만원대 )
        assertEquals("1,000,000", result)
    }

    @Test
    fun revert(){
        val result = MoneyFormatter.revert("1,000,000")
        assertEquals(1000000, result)
    }

    @Test
    fun revertWithUnit(){
        val result = MoneyFormatter.revert("1,000,000원", "원")
        assertEquals(1000000, result)
    }
}