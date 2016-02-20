package com.antyzero.smoksmog.ui;

import android.app.Service;
import android.content.Context;

import com.antyzero.smoksmog.BuildConfig;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.error.ServiceDebugErrorReporter;
import com.antyzero.smoksmog.error.SilentErrorReporter;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule( Service service ) {
        this.service = service;
    }

    @Provides
    public Service provideService() {
        return service;
    }

    @Provides
    public ErrorReporter provideErrorReporter( final Context context) {
        return BuildConfig.DEBUG ? new ServiceDebugErrorReporter( context ) : new SilentErrorReporter();
    }
}
