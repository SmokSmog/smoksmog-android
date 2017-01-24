package smoksmog.logger

import android.util.Log

/**
 * Typical Android SDK logging
 */
class AndroidLogger : Logger {

    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun v(tag: String, message: String, throwable: Throwable) {
        Log.v(tag, message, throwable)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        Log.d(tag, message, throwable)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        Log.i(tag, message, throwable)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        Log.w(tag, message, throwable)
    }

    override fun e(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        Log.v(tag, message, throwable)
    }


}
