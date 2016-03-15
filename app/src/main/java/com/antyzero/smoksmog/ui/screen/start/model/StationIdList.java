package com.antyzero.smoksmog.ui.screen.start.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.permission.PermissionHelper;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.utils.ForwardingList;

import java.util.List;

import javax.inject.Inject;

public class StationIdList extends ForwardingList<Long> implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    SettingsHelper settingsHelper;
    @Inject
    PermissionHelper permissionHelper;

    public StationIdList( Context context ) {
        SmokSmogApplication.get( context )
                .getAppComponent()
                .inject( this );

        SharedPreferences settingsPreferences = settingsHelper.getPreferences();
        settingsPreferences.registerOnSharedPreferenceChangeListener( this );
        updateWithPreferences( settingsPreferences );
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        if ( key.equals( settingsHelper.getKeyStationClosest() ) ) {
            updateWithPreferences( sharedPreferences );
        }
    }

    @Override
    protected List<Long> delegate() {
        return settingsHelper.getStationIdList();
    }

    private void updateWithPreferences( SharedPreferences sharedPreferences ) {
        boolean isClosestStation = sharedPreferences.getBoolean( settingsHelper.getKeyStationClosest(), false );
        if ( isClosestStation && permissionHelper.isGrantedLocationCorsare() ) {
            if ( size() > 0 && get( 0 ) != 0 ) {
                add( 0, 0L );
            } else if ( size() <= 0 ) {
                add( 0L );
            }
        } else {
            if ( size() > 0 && get( 0 ) == 0 ) {
                remove( 0 );
            }
        }
    }
}
