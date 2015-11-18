package com.antyzero.smoksmog.logger;

import android.util.Log;

/**
 * Created by iwopolanski on 17.11.15.
 */
public class LevelBlockingLogger implements Logger {

    private final Logger logger;
    private final int minLevelToLog;

    /**
     * @param logger
     * @param minLevelToLog use Log.* value / level, eg. Log.ERROR
     */
    public LevelBlockingLogger( Logger logger, int minLevelToLog ) {
        this.logger = logger;
        this.minLevelToLog = minLevelToLog;
    }

    @Override
    public void v( String tag, String message ) {
        if ( minLevelToLog >= Log.VERBOSE ) {
            logger.v( tag, message );
        }
    }

    @Override
    public void v( String tag, String message, Throwable throwable ) {
        if ( minLevelToLog >= Log.VERBOSE ) {
            logger.v( tag, message, throwable );
        }
    }

    @Override
    public void d( String tag, String message ) {
        if ( minLevelToLog >= Log.DEBUG ) {
            logger.d( tag, message );
        }
    }

    @Override
    public void d( String tag, String message, Throwable throwable ) {
        if ( minLevelToLog >= Log.DEBUG ) {
            logger.d( tag, message, throwable );
        }
    }

    @Override
    public void i( String tag, String message ) {
        if ( minLevelToLog >= Log.INFO ) {
            logger.i( tag, message );
        }
    }

    @Override
    public void i( String tag, String message, Throwable throwable ) {
        if ( minLevelToLog >= Log.INFO ) {
            logger.i( tag, message, throwable );
        }
    }

    @Override
    public void w( String tag, String message ) {
        if ( minLevelToLog >= Log.WARN ) {
            logger.w( tag, message );
        }
    }

    @Override
    public void w( String tag, String message, Throwable throwable ) {
        if ( minLevelToLog >= Log.WARN ) {
            logger.w( tag, message, throwable );
        }
    }

    @Override
    public void e( String tag, String message ) {
        if ( minLevelToLog >= Log.ERROR ) {
            logger.e( tag, message );
        }
    }

    @Override
    public void e( String tag, String message, Throwable throwable ) {
        if ( minLevelToLog >= Log.ERROR ) {
            logger.e( tag, message, throwable );
        }
    }
}
