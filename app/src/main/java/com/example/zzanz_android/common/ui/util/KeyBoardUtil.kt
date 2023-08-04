package com.example.zzanz_android.common.ui.util

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun keyboardAsState(): MutableState<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val imeVisible =
                ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime())
                    ?: true
            keyboardState.value = imeVisible
            val imeHeight =
                ViewCompat.getRootWindowInsets(view)?.getInsets(WindowInsetsCompat.Type.ime())
            Log.d(
                "### keyboardAsState()",
                "keyboardHeight : $imeHeight keyboardVisibility: $imeVisible"
            )
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return keyboardState
}

@Composable
fun keyboardHeightAsState(): MutableState<Int> {
    val keyboardHeightState = remember { mutableStateOf(0) }
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val imeVisible =
                ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime())
                    ?: true
            val imeHeight =
                ViewCompat.getRootWindowInsets(view)?.getInsets(WindowInsetsCompat.Type.ime())
            Log.d(
                "### keyboardHeightAsState()",
                "keyboardHeight : ${imeHeight?.bottom}"
            )
            imeHeight?.let {
                keyboardHeightState.value = it.bottom
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return keyboardHeightState
}