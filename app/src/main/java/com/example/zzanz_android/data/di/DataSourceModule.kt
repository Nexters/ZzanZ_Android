package com.example.zzanz_android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {
    @Provides
    @Named("planId")
    fun providePlanId(): Int = DataHolder.planId
}

object DataHolder {
    @Volatile
    var planId: Int = -1
}