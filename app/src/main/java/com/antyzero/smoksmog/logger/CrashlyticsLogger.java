package com.antyzero.smoksmog.logger;

import android.util.Log;

import com.crashlytics.android.core.CrashlyticsCore;

import smoksmog.logger.Logger;

/**
 * Crashlytics logger
 */
public class CrashlyticsLogger implements Logger {

    private final static ConfigurationCallback EMPTY_CALLBACK = instance -> {};

    private final ExceptionLevel logExceptionLevel;

    public CrashlyticsLogger() {
        this( ExceptionLevel.VERBOSE, EMPTY_CALLBACK);
    }

    public CrashlyticsLogger(ExceptionLevel logExceptionLevel, ConfigurationCallback callback ) {
        this.logExceptionLevel = logExceptionLevel;
        callback.onConfiguration(CrashlyticsCore.getInstance());
    }

    @Override
    public void v( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.VERBOSE, tag, message );
    }

    @Override
    public void v( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.VERBOSE, tag, message );
        if ( logExceptionLevel.value <= ExceptionLevel.VERBOSE.value ) {
            CrashlyticsCore.getInstance().logException( throwable );
        }
    }

    @Override
    public void d( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.DEBUG, tag, message );
    }

    @Override
    public void d( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.DEBUG, tag, message );
        if ( logExceptionLevel.value <= ExceptionLevel.DEBUG.value ) {
            CrashlyticsCore.getInstance().logException( throwable );
        }
    }

    @Override
    public void i( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.INFO, tag, message );
    }

    @Override
    public void i( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.INFO, tag, message );
        if ( logExceptionLevel.value <= ExceptionLevel.INFO.value ) {
            CrashlyticsCore.getInstance().logException( throwable );
        }
    }

    @Override
    public void w( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.WARN, tag, message );
    }

    @Override
    public void w( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.WARN, tag, message );
        if ( logExceptionLevel.value <= ExceptionLevel.WARN.value ) {
            CrashlyticsCore.getInstance().logException( throwable );
        }
    }

    @Override
    public void e( String tag, String message ) {
        CrashlyticsCore.getInstance().log( Log.ERROR, tag, message );
    }

    @Override
    public void e( String tag, String message, Throwable throwable ) {
        CrashlyticsCore.getInstance().log( Log.ERROR, tag, message );
        if ( logExceptionLevel.value <= ExceptionLevel.ERROR.value ) {
            CrashlyticsCore.getInstance().logException( throwable );
        }
    }

    public enum ExceptionLevel {
        VERBOSE( 0 ), DEBUG( 1 ), INFO( 2 ), WARN( 3 ), ERROR( 4 );

        private final int value;

        ExceptionLevel( int value ) {
            this.value = value;
        }
    }

    public interface ConfigurationCallback {
        void onConfiguration(CrashlyticsCore instance);
    }
}
