package com.antyzero.smoksmog.settings;

import android.content.Context;

import com.antyzero.smoksmog.permission.PermissionHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SettingsModule {

    @Provides
    @Singleton
    @Deprecated
    public SettingsHelper provideSettingsHelper( Context context, PermissionHelper permissionHelper ) {
        return new SettingsHelper( context, permissionHelper );
    }
}
