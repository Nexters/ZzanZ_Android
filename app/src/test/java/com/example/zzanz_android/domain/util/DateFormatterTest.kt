package com.example.zzanz_android.domain.util

import org.junit.Assert.*

import org.junit.Test

class DateFormatterTest {

    @Test
    fun format() {
        val result = DateFormatter.format("2020-04-03T23:59:59-04:00")
        assertEquals("4.3", result)
    }
}