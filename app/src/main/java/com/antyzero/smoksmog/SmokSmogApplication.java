package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.antyzero.smoksmog.logger.Logger;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Inject
    Logger logger;

    @Override
    public void onCreate() {
        super.onCreate();
        
        Fabric.with( this, new CrashlyticsCore.Builder()
                .disabled( BuildConfig.DEBUG )
                .build(), new Answers() );

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

        applicationComponent.inject( this );

        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                //.setDefaultFontPath( "fonts/Roboto-Regular.ttf" )
                .build() );
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    /**
     * Kept to inject mocked components.
     *
     * @param applicationComponent for replace
     */
    @VisibleForTesting
    public void setAppComponent( ApplicationComponent applicationComponent ) {
        this.applicationComponent = applicationComponent;
    }

    /**
     * Get access to application instance
     *
     * @param context
     * @return
     */
    public static SmokSmogApplication get( Context context ) {
        return ( SmokSmogApplication ) context.getApplicationContext();
    }
}
