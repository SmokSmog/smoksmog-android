package com.antyzero.smoksmog.logger;

import android.util.Log;

import com.crashlytics.android.core.CrashlyticsCore;

/**
 * Crashlytics logger
 */
public class CrashlyticsLogger implements Logger {



    @Override
    public void v( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.VERBOSE, tag, message );
    }

    @Override
    public void v( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.VERBOSE, tag, message );
        CrashlyticsCore.getInstance().logException( throwable );
    }

    @Override
    public void d( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.DEBUG, tag, message );
    }

    @Override
    public void d( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.DEBUG, tag, message );
        CrashlyticsCore.getInstance().logException( throwable );
    }

    @Override
    public void i( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.INFO, tag, message );
    }

    @Override
    public void i( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.INFO, tag, message );
        CrashlyticsCore.getInstance().logException( throwable );
    }

    @Override
    public void w( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.WARN, tag, message );
    }

    @Override
    public void w( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.WARN, tag, message );
        CrashlyticsCore.getInstance().logException( throwable );
    }

    @Override
    public void e( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.ERROR, tag, message );
    }

    @Override
    public void e( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.ERROR, tag, message );
        CrashlyticsCore.getInstance().logException( throwable );
    }
}
