package com.antyzero.smoksmog.sync;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class StationNotificationModule {

    @Provides
    IStationNotificationHandler providesStationNotificationHandler(Context context) {
        return new StationNotificationHandler(context);
    }
}
