package com.example.zzanz_android.common.ui.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    LaunchedEffect(view) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val navigationBarHeight =
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            val keyboardHeight = imeHeight - navigationBarHeight
            if (imeVisible && keyboardHeight > 0) {
                keyboardState.value = (keyboardHeight != 0)
            } else {
                keyboardState.value = false
            }
            Log.d(
                "### keyboardAsState()",
                "keyboardHeight : $keyboardHeight keyboardVisibility: $imeVisible"
            )
            insets
        }
    }
    return keyboardState
}