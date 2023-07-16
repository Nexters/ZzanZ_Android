package com.example.zzanz_android.data.mapper

interface Mapper<D, M> {
    fun toData(model: M): D
    fun toModel(data: D): M
}