package com.example.zzanz_android.data.mapper

interface Mapper<D, M> {
    fun D.toModel(): M
}