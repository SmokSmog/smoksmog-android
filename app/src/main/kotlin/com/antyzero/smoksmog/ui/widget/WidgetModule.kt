package com.antyzero.smoksmog.ui.widget


import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
@Singleton
class WidgetModule {

    @Provides
    @Singleton
    fun provideStationWidgetData(context: Context): StationWidgetData {
        return StationWidgetData(context)
    }
}
