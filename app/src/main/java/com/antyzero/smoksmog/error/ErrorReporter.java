package com.antyzero.smoksmog.error;

import android.support.annotation.StringRes;

/**
 * Common interface for ui elements to report errors
 */
public interface ErrorReporter {

    void report( String message );

    void report( @StringRes int stringId );

    void report( @StringRes int stringId, Object... objects );
}
