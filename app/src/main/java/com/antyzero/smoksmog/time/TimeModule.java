package com.antyzero.smoksmog.time;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class TimeModule {

    @Provides
    @Singleton
    public CountdownProvider provideCountdownProvider( Context context ) {
        return new CountdownProvider( context );
    }
}
