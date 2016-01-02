package com.antyzero.smoksmog.ui.screen.settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.antyzero.smoksmog.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setSupportActionBar( (Toolbar) getLayoutInflater().inflate( R.layout.toolbar, null, false ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onMenuItemSelected( int featureId, MenuItem item ) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            if ( !super.onMenuItemSelected( featureId, item ) ) {
                NavUtils.navigateUpFromSameTask( this );
            }
            return true;
        }
        return super.onMenuItemSelected( featureId, item );
    }

    /**
     *
     *
     * @param context for starting
     */
    public static void start( Context context ) {
        context.startActivity( intent( context ) );
    }

    @NonNull
    private static Intent intent( Context context ) {
        return new Intent( context, SettingsActivity.class );
    }
}
