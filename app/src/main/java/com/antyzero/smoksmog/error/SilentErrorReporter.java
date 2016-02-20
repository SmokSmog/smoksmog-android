package com.antyzero.smoksmog.error;

import android.support.annotation.StringRes;

/**
 * A {@link ErrorReporter} implementation which does nothing. Useful for release builds where
 * you might not want to harass users by displaying error messages.
 */
public class SilentErrorReporter implements ErrorReporter {
    @Override
    public void report(String message) {

    }

    @Override
    public void report(@StringRes int stringId) {

    }

    @Override
    public void report(@StringRes int stringId, Object... objects) {

    }
}
