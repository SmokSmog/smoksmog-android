package com.antyzero.smoksmog.ui.widget


import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class WidgetModule {

    @Provides
    @Singleton
    fun provideStationWidgetData(context: Context): StationWidgetData {
        return StationWidgetData(context)
    }
}
