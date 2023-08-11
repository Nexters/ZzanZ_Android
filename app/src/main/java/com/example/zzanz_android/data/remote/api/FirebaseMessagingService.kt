package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject

class FirebaseMessagingService @Inject constructor(
    private val userPrefApi: UserPreferenceServiceImpl
) : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.d("FirebaseMessagingService New token :: $token")
        suspend {
            saveFcmToken(token)
        }
    }

    private suspend fun saveFcmToken(token: String) {
        val result = userPrefApi.setFcmToken(token)
        if (result == Resource.Success(Boolean)) {
            if (result == Resource.Success(true)) {
                GlobalUiEvent.showToast("FirebaseMessagingService saveFcmToken Success")
            } else {
                GlobalUiEvent.showToast("FirebaseMessagingService saveFcmToken Success Error - $result")
            }
        } else {
            GlobalUiEvent.showToast("FirebaseMessagingService saveFcmToken Failed Error - $result")
        }
    }

    private fun sendTokenToServer(token: String) {
        Timber.d("FirebaseMessagingService sendTokenToServer: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("FirebaseMessagingService Notification Title :: ${remoteMessage.notification?.title}")
        Timber.d("FirebaseMessagingService Notification Body :: ${remoteMessage.notification?.body}")
        Timber.d("FirebaseMessagingService Notification Data :: ${remoteMessage.data}")
    }
}