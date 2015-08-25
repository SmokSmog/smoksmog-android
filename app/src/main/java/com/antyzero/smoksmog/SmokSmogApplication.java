package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;

public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();
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
