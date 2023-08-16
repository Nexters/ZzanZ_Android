package com.zzanz.swip_android.domain.model

data class SpendingModel(
    val id: Int,
    val title: String,
    val memo: String?,
    val amount: Int
)