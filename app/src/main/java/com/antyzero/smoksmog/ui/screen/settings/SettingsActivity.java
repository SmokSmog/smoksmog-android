package com.antyzero.smoksmog.ui.screen.settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.BaseActivity;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        BaseActivity.initOnCreate( this );

        getFragmentManager().beginTransaction()
                .replace( android.R.id.content, GeneralSettingsFragment.create() )
                .commit();
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        // TODO f... found on stack but this is ugly approach to get root view
        LinearLayout root = (LinearLayout) findViewById( android.R.id.list ).getParent().getParent().getParent();
        Toolbar toolbar = (Toolbar) LayoutInflater.from( this ).inflate( R.layout.toolbar, root, false );
        root.addView( toolbar, 0 ); // insert at top
        toolbar.setNavigationOnClickListener( v -> finish() );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( R.string.title_settings );
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
