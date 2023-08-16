package com.zzanz.swip_android.data.util

import com.zzanz.swip_android.BuildConfig

object AppVersionUtil {
    const val OsName = "Android"

    fun getVersionName(): String{
        val versions = BuildConfig.VERSION_NAME.split(".")
        val (major, minor) = Pair(versions[0], versions[1])
        return "${major}.${minor}"
    }
}