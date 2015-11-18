package com.antyzero.smoksmog.logger;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * Crashlytics logger
 */
public class CrashlyticsLogger implements Logger {



    @Override
    public void v( String tag, String message ) {
        Crashlytics.log( Log.VERBOSE, tag, message );
    }

    @Override
    public void v( String tag, String message, Throwable throwable ) {
        Crashlytics.log( Log.VERBOSE, tag, message );
        Crashlytics.logException( throwable );
    }

    @Override
    public void d( String tag, String message ) {
        Crashlytics.log( Log.DEBUG, tag, message );
    }

    @Override
    public void d( String tag, String message, Throwable throwable ) {
        Crashlytics.log( Log.DEBUG, tag, message );
        Crashlytics.logException( throwable );
    }

    @Override
    public void i( String tag, String message ) {
        Crashlytics.log( Log.INFO, tag, message );
    }

    @Override
    public void i( String tag, String message, Throwable throwable ) {
        Crashlytics.log( Log.INFO, tag, message );
        Crashlytics.logException( throwable );
    }

    @Override
    public void w( String tag, String message ) {
        Crashlytics.log( Log.WARN, tag, message );
    }

    @Override
    public void w( String tag, String message, Throwable throwable ) {
        Crashlytics.log( Log.WARN, tag, message );
        Crashlytics.logException( throwable );
    }

    @Override
    public void e( String tag, String message ) {
        Crashlytics.log( Log.ERROR, tag, message );
    }

    @Override
    public void e( String tag, String message, Throwable throwable ) {
        Crashlytics.log( Log.ERROR, tag, message );
        Crashlytics.logException( throwable );
    }
}
