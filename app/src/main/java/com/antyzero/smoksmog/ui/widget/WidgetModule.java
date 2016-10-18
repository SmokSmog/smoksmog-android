package com.antyzero.smoksmog.ui.widget;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class WidgetModule {

    @Provides
    @Singleton
    public StationWidgetData provideStationWidgetData(Context context) {
        return new StationWidgetData(context);
    }
}
