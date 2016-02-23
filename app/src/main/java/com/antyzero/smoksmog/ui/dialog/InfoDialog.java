package com.antyzero.smoksmog.ui.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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

        if ( getLayoutId() <= 0 && !getArguments().containsKey( KEY_LAYOUT_ID ) ) {
            throw new IllegalStateException( "Layout ID is not provided via argument or overridden method" );
        }
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        View view = getActivity().getLayoutInflater().inflate( getLayoutId(), null, false );
        initView( view );
        builder.setView( view );
        builder.setPositiveButton( android.R.string.ok, ( dialog, which ) -> {
            dialog.dismiss();
        } );
        return builder.create();
    }

    protected void initView( View view ) {
        // override if needed
    }

    @LayoutRes
    protected int getLayoutId() {
        return getArguments().getInt( KEY_LAYOUT_ID );
    }

    public static void show( FragmentManager fragmentManager, Event event ) {
        InfoDialog infoDialog;

        if ( event.dialogFragment == null ) {
            infoDialog = new InfoDialog();
        } else {
            try {
                infoDialog = ( InfoDialog ) event.dialogFragment.newInstance();
            } catch ( Exception e ) {
                throw new IllegalStateException(
                        "Problem with creating fragment dialog " + event.dialogFragment.getSimpleName(), e );
            }
        }

        Bundle bundle = new Bundle();
        if ( event.layoutId > 0 ) {
            bundle.putInt( KEY_LAYOUT_ID, event.layoutId );
        }
        infoDialog.setArguments( bundle );
        infoDialog.show( fragmentManager, TAG );
    }

    public static class Event<T extends InfoDialog> {

        public final int layoutId;
        @Nullable
        private final Class<T> dialogFragment;

        public Event( @Nullable @LayoutRes int layoutId ) {
            this( layoutId, null );
        }

        public Event( @Nullable Class<T> dialogFragment ) {
            this( 0, dialogFragment );
        }

        public Event( @Nullable @LayoutRes int layoutId, @Nullable Class<T> dialogFragment ) {
            this.layoutId = layoutId;
            this.dialogFragment = dialogFragment;
        }
    }
}
