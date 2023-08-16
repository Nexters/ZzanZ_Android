package com.zzanz.swip_android.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object EmailManager {
    private const val emailAddress = "zzanzswip@gmail.com"
    fun sendEmail(context: Context){
        val uriStr = "mailto:$emailAddress"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(uriStr)
        }
        context.startActivity(intent)
    }

}