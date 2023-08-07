package com.example.zzanz_android.data.util

import com.example.zzanz_android.BuildConfig

object AppVersionUtil {
    fun getVersionHeader(): String{
        return "Android/${getVersionName()}"
    }

    private fun getVersionName(): String{
        val versions = BuildConfig.VERSION_NAME.split(".")
        val (major, minor) = Pair(versions[0], versions[1])
        return "${major}.${minor}"
    }
}