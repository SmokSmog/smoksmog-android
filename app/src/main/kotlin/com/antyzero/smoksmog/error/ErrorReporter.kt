package com.antyzero.smoksmog.error

import android.support.annotation.StringRes

/**
 * Common interface for ui elements to report errors
 */
interface ErrorReporter {

    fun report(message: String)

    fun report(@StringRes stringId: Int)

    fun report(@StringRes stringId: Int, vararg objects: Any)
}
