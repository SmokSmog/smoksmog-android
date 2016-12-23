package smoksmog.logger

import android.util.Log

class LevelBlockingLogger(private val logger: Logger, private val lowestLevelToLog: Int) : Logger {

    override fun v(tag: String, message: String) {
        if (lowestLevelToLog >= Log.VERBOSE) {
            logger.v(tag, message)
        }
    }

    override fun v(tag: String, message: String, throwable: Throwable) {
        if (lowestLevelToLog >= Log.VERBOSE) {
            logger.v(tag, message, throwable)
        }
    }

    override fun d(tag: String, message: String) {
        if (lowestLevelToLog >= Log.DEBUG) {
            logger.d(tag, message)
        }
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        if (lowestLevelToLog >= Log.DEBUG) {
            logger.d(tag, message, throwable)
        }
    }

    override fun i(tag: String, message: String) {
        if (lowestLevelToLog >= Log.INFO) {
            logger.i(tag, message)
        }
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        if (lowestLevelToLog >= Log.INFO) {
            logger.i(tag, message, throwable)
        }
    }

    override fun w(tag: String, message: String) {
        if (lowestLevelToLog >= Log.WARN) {
            logger.w(tag, message)
        }
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        if (lowestLevelToLog >= Log.WARN) {
            logger.w(tag, message, throwable)
        }
    }

    override fun e(tag: String, message: String) {
        if (lowestLevelToLog >= Log.ERROR) {
            logger.e(tag, message)
        }
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        if (lowestLevelToLog >= Log.ERROR) {
            logger.e(tag, message, throwable)
        }
    }
}
