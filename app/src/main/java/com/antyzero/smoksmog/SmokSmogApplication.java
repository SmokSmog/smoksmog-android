package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;

public class SmokSmogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
