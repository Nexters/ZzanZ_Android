package com.example.zzanz_android.common

sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    object Success : NetworkState()
}