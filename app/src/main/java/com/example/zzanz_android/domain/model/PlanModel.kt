package com.example.zzanz_android.domain.model

data class PlanModel(
    val id: Int,
    val category: String,
    val goalAmount: Int,
    val remainAmount: Int
)