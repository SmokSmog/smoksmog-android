package com.antyzero.smoksmog.error;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ServiceDebugErrorReporter implements ErrorReporter {

    private Context context;

    public ServiceDebugErrorReporter( final Context context ) {
        this.context = context;
    }

    @Override
    public void report(String message) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void report(@StringRes int stringId) {
        Toast.makeText( context, stringId, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void report(@StringRes int stringId, Object... objects) {
        final String message = context.getResources().getString( stringId, objects );
        report(message);
    }
}
