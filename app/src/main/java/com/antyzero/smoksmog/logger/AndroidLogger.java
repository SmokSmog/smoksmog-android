package com.antyzero.smoksmog.logger;

import android.util.Log;

/**
 * Typical Android SDK loggining
 */
public class AndroidLogger implements Logger {

    @Override
    public void v( String tag, String message ) {
        Log.v( tag, message );
    }

    @Override
    public void v( String tag, String message, Throwable throwable ) {
        Log.v( tag, message, throwable );
    }

    @Override
    public void d( String tag, String message ) {
        Log.d( tag, message );
    }

    @Override
    public void d( String tag, String message, Throwable throwable ) {
        Log.d( tag, message, throwable );
    }

    @Override
    public void i( String tag, String message ) {
        Log.i( tag, message );
    }

    @Override
    public void i( String tag, String message, Throwable throwable ) {
        Log.i( tag, message, throwable );
    }

    @Override
    public void w( String tag, String message ) {
        Log.w( tag, message );
    }

    @Override
    public void w( String tag, String message, Throwable throwable ) {
        Log.w( tag, message, throwable );
    }

    @Override
    public void e( String tag, String message ) {
        Log.v( tag, message );
    }

    @Override
    public void e( String tag, String message, Throwable throwable ) {
        Log.v( tag, message, throwable );
    }


}
