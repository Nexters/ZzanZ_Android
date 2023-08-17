package com.zzanz.swip_android.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object BrowserManager {
    private const val feedbackUrl = "https://sotitch.notion.site/W1P-6f066c93d1e84b3b9e678eba199cd9b3?pvs=4"
    private const val termsUrl = "https://sotitch.notion.site/08514429a15b4ff680d84c0a03cce29a?pvs=25"
    fun openFeedbackPage(context: Context){
        openBrowser(context, feedbackUrl)
    }

    fun openTermsPage(context: Context){
        openBrowser(context, termsUrl)
    }

    private fun openBrowser(context: Context, url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}