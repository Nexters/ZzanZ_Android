package com.zzanz.swip_android.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object AuthorizationManager {
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String{
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}