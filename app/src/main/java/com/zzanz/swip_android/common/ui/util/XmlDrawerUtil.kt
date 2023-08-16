package com.zzanz.swip_android.common.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds

@Composable
fun ImageViewWithXml(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    resId: Int,
    content: @Composable () -> Unit = {}
) {
    val res = ContextCompat.getDrawable(LocalContext.current, resId)
    Box(modifier = modifier
        .drawBehind {
            res?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
            res?.draw(drawContext.canvas.nativeCanvas)
        }, contentAlignment = contentAlignment
    ) {
        content()
    }
}