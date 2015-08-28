package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                //.setDefaultFontPath( "fonts/Roboto-Regular.ttf" )
                .build() );
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    /**
     * Get access to application instance
     *
     * @param context
     * @return
     */
    public static final SmokSmogApplication get( Context context ) {
        return (SmokSmogApplication) context.getApplicationContext();
    }
}
