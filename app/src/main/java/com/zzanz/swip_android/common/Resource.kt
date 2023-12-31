package com.zzanz.swip_android.common

import java.lang.Exception

sealed class Resource<out T> {
    class Success<T>(val data: T): Resource<T>()
    class Error(val exception: Exception): Resource<Nothing>()
}