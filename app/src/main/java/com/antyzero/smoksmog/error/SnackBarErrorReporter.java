package com.antyzero.smoksmog.error;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

/**
 * Error reporting via Snackbar
 */
public class SnackBarErrorReporter implements ErrorReporter {

    private static final int DURATION = Snackbar.LENGTH_LONG;

    private final Activity activity;

    public SnackBarErrorReporter( Activity activity ) {
        this.activity = activity;
    }

    @Override
    public void report( String message ) {
        processSnackbar( Snackbar.make( activity.findViewById( android.R.id.content ), message, DURATION ) );
    }

    @Override
    public void report( @StringRes int stringId ) {
        processSnackbar( Snackbar.make( activity.findViewById( android.R.id.content ), stringId, DURATION ) );
    }

    @Override
    public void report( @StringRes int stringId, Object... objects ) {
        String message = activity.getResources().getString( stringId, objects );
        processSnackbar( Snackbar.make( activity.findViewById( android.R.id.content ), message, DURATION ) );
    }

    private void processSnackbar( Snackbar snackbar ) {
        snackbar.show();
    }
}
