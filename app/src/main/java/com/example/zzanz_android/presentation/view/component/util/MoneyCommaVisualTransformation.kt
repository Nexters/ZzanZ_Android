package com.example.zzanz_android.presentation.view.component.util

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.zzanz_android.domain.util.MoneyFormatter

class MoneyCommaVisualTransformation(val unit: String, private val isShowUnit: Boolean) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var result = MoneyFormatter.format(text.text)
        return TransformedText(text = AnnotatedString(
            if (text.isEmpty()) {
                ""
            } else {
                if (isShowUnit) "$result $unit" else "$result"
            }
        ), offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return result.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.length
            }

        })
    }
}