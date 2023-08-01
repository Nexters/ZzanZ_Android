package com.example.zzanz_android.data.mapper

interface MapperToModel<D, M> {
    fun D.toModel(): M

}

interface MapperToDto<D, M> {
    fun M.toDto() : D
}