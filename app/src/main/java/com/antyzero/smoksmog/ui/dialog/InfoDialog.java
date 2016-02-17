package com.antyzero.smoksmog.ui.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * For info dialog
 */
public class InfoDialog extends DialogFragment {

    private static final String TAG = InfoDialog.class.getSimpleName();
    private static final String KEY_LAYOUT_ID = "keyLayout";

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        if ( !getArguments().containsKey( KEY_LAYOUT_ID ) ) {
            throw new IllegalStateException( "Show dialog with show() method" );
        }
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        View view = getActivity().getLayoutInflater().inflate( getLayoutId(), null, false );
        builder.setView( view );
        builder.setPositiveButton( android.R.string.ok, ( dialog, which ) -> {
            dialog.dismiss();
        } );
        return builder.create();
    }

    @LayoutRes
    private int getLayoutId() {
        return getArguments().getInt( KEY_LAYOUT_ID );
    }

    public void show( FragmentManager fragmentManager, @LayoutRes int lauoutId ) {
        InfoDialog infoDialog = new InfoDialog();
        Bundle bundle = new Bundle();
        bundle.putInt( KEY_LAYOUT_ID, lauoutId );
        infoDialog.setArguments( bundle );
        infoDialog.show( fragmentManager, TAG );
    }

    public static class Event{

        public final int layoutId;

        public Event( @LayoutRes int layoutId) {
            this.layoutId = layoutId;
        }
    }
}
