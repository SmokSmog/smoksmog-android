package com.antyzero.smoksmog.logger

import android.util.Log

import com.crashlytics.android.core.CrashlyticsCore

import smoksmog.logger.Logger

/**
 * Crashlytics logger
 */
class CrashlyticsLogger @JvmOverloads constructor(
        private val logExceptionLevel: CrashlyticsLogger.ExceptionLevel = CrashlyticsLogger.ExceptionLevel.VERBOSE,
        callback: CrashlyticsLogger.ConfigurationCallback = CrashlyticsLogger.EMPTY_CALLBACK) : Logger {

    init {
        callback.onConfiguration(CrashlyticsCore.getInstance())
    }

    override fun v(tag: String, message: String) {
        CrashlyticsCore.getInstance().log(Log.VERBOSE, tag, message)
    }

    override fun v(tag: String, message: String, throwable: Throwable) {
        CrashlyticsCore.getInstance().log(Log.VERBOSE, tag, message)
        if (logExceptionLevel.value <= ExceptionLevel.VERBOSE.value) {
            CrashlyticsCore.getInstance().logException(throwable)
        }
    }

    override fun d(tag: String, message: String) {
        CrashlyticsCore.getInstance().log(Log.DEBUG, tag, message)
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        CrashlyticsCore.getInstance().log(Log.DEBUG, tag, message)
        if (logExceptionLevel.value <= ExceptionLevel.DEBUG.value) {
            CrashlyticsCore.getInstance().logException(throwable)
        }
    }

    override fun i(tag: String, message: String) {
        CrashlyticsCore.getInstance().log(Log.INFO, tag, message)
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        CrashlyticsCore.getInstance().log(Log.INFO, tag, message)
        if (logExceptionLevel.value <= ExceptionLevel.INFO.value) {
            CrashlyticsCore.getInstance().logException(throwable)
        }
    }

    override fun w(tag: String, message: String) {
        CrashlyticsCore.getInstance().log(Log.WARN, tag, message)
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        CrashlyticsCore.getInstance().log(Log.WARN, tag, message)
        if (logExceptionLevel.value <= ExceptionLevel.WARN.value) {
            CrashlyticsCore.getInstance().logException(throwable)
        }
    }

    override fun e(tag: String, message: String) {
        CrashlyticsCore.getInstance().log(Log.ERROR, tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        CrashlyticsCore.getInstance().log(Log.ERROR, tag, message)
        if (logExceptionLevel.value <= ExceptionLevel.ERROR.value) {
            CrashlyticsCore.getInstance().logException(throwable)
        }
    }

    enum class ExceptionLevel constructor(internal val value: Int) {
        VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4)
    }

    interface ConfigurationCallback {
        fun onConfiguration(instance: CrashlyticsCore)
    }

    companion object {

        private val EMPTY_CALLBACK = object : ConfigurationCallback {
            override fun onConfiguration(instance: CrashlyticsCore) {

            }
        }
    }
}
