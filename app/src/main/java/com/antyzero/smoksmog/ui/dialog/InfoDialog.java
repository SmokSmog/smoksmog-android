package com.antyzero.smoksmog.ui.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * For info dialog
 */
public abstract class InfoDialog extends DialogFragment {

    private static final String TAG = InfoDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(getLayoutId(), null, false);
        initView(view);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
        });
        return builder.create();
    }

    protected void initView(View view) {
        // override if needed
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public static void show(FragmentManager fragmentManager, Event event) {
        InfoDialog infoDialog;

        try {
            infoDialog = (InfoDialog) event.dialogFragment.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Problem with creating fragment dialog " + event.dialogFragment.getSimpleName(), e);
        }

        infoDialog.show(fragmentManager, TAG);
    }

    public static class Event<T extends InfoDialog> {

        @NonNull
        private final Class<T> dialogFragment;

        public Event(@NonNull Class<T> dialogFragment) {
            this.dialogFragment = dialogFragment;
        }
    }
}
