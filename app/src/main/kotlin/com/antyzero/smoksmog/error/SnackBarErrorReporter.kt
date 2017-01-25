package com.antyzero.smoksmog.error

import android.app.Activity
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar

/**
 * Error reporting via SnackBar
 */
class SnackBarErrorReporter(private val activity: Activity) : ErrorReporter {

    override fun report(message: String) {
        processSnackBar(Snackbar.make(activity.findViewById(android.R.id.content), message, DURATION))
    }

    override fun report(@StringRes stringId: Int) {
        processSnackBar(Snackbar.make(activity.findViewById(android.R.id.content), stringId, DURATION))
    }

    override fun report(@StringRes stringId: Int, vararg objects: Any) {
        val message = activity.resources.getString(stringId, *objects)
        processSnackBar(Snackbar.make(activity.findViewById(android.R.id.content), message, DURATION))
    }

    private fun processSnackBar(snackBar: Snackbar) {
        snackBar.show()
    }

    companion object {
        private val DURATION = Snackbar.LENGTH_LONG
    }
}
