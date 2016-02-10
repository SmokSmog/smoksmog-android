package com.antyzero.smoksmog.settings;

import android.content.Context;

import com.antyzero.smoksmog.settingsold.SettingsOldHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SettingsModule {

    @Provides
    @Singleton @Deprecated
    public SettingsOldHelper provideSettingsHelper( Context context ) {
        return new SettingsOldHelper( context );
    }
}
