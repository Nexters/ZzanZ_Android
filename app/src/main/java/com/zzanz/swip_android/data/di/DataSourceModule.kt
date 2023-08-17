package com.zzanz.swip_android.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {
    @Provides
    @Named("planId")
    fun providePlanId(): Int = DataHolder.planId

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}

object DataHolder {
    @Volatile
    var planId: Int = -1
}