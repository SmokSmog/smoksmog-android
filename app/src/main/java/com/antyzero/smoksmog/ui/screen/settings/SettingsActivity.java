package com.antyzero.smoksmog.ui.screen.settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.BaseActivity;

import butterknife.Bind;

public class SettingsActivity extends BaseActivity {

    @Bind( R.id.toolbar )
    Toolbar toolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        setSupportActionBar( toolbar );

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setTitle( R.string.title_settings );
        }

        getFragmentManager().beginTransaction()
                .replace( R.id.contentFragment, GeneralSettingsFragment.create() )
                .commit();
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
