package smoksmog.logger;

import android.util.Log;

public class LevelBlockingLogger implements Logger {

    private final Logger logger;
    private final int lowestLevelToLog;

    /**
     * @param logger           implementation
     * @param lowestLevelToLog use Log.* value / level, eg. Log.ERROR
     */
    public LevelBlockingLogger(Logger logger, int lowestLevelToLog) {
        this.logger = logger;
        this.lowestLevelToLog = lowestLevelToLog;
    }

    @Override
    public void v(String tag, String message) {
        if (lowestLevelToLog >= Log.VERBOSE) {
            logger.v(tag, message);
        }
    }

    @Override
    public void v(String tag, String message, Throwable throwable) {
        if (lowestLevelToLog >= Log.VERBOSE) {
            logger.v(tag, message, throwable);
        }
    }

    @Override
    public void d(String tag, String message) {
        if (lowestLevelToLog >= Log.DEBUG) {
            logger.d(tag, message);
        }
    }

    @Override
    public void d(String tag, String message, Throwable throwable) {
        if (lowestLevelToLog >= Log.DEBUG) {
            logger.d(tag, message, throwable);
        }
    }

    @Override
    public void i(String tag, String message) {
        if (lowestLevelToLog >= Log.INFO) {
            logger.i(tag, message);
        }
    }

    @Override
    public void i(String tag, String message, Throwable throwable) {
        if (lowestLevelToLog >= Log.INFO) {
            logger.i(tag, message, throwable);
        }
    }

    @Override
    public void w(String tag, String message) {
        if (lowestLevelToLog >= Log.WARN) {
            logger.w(tag, message);
        }
    }

    @Override
    public void w(String tag, String message, Throwable throwable) {
        if (lowestLevelToLog >= Log.WARN) {
            logger.w(tag, message, throwable);
        }
    }

    @Override
    public void e(String tag, String message) {
        if (lowestLevelToLog >= Log.ERROR) {
            logger.e(tag, message);
        }
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        if (lowestLevelToLog >= Log.ERROR) {
            logger.e(tag, message, throwable);
        }
    }
}
