package com.antyzero.smoksmog.ui.screen.start.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.utils.ForwardingList;

import java.util.List;

import javax.inject.Inject;

public class StationIdList extends ForwardingList<Long> implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final SharedPreferences preferences;

    @Inject
    SettingsHelper settingsHelper;

    public StationIdList( Context context ) {
        SmokSmogApplication.get( context )
                .getAppComponent()
                .inject( this );

        preferences = settingsHelper.getPreferences();
        preferences.registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    public Long get( int location ) {
        if ( settingsHelper.isClosesStationVisible() ) {
            return location == 0 ? 0L : super.get( location - 1 );
        } else {
            return super.get( location );
        }
    }

    @Override
    public int size() {
        return super.size() + ( settingsHelper.isClosesStationVisible() ? 1 : 0 );
    }

    @Override
    protected List<Long> delegate() {
        return settingsHelper.getStationIdList();
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        if( key.equals( settingsHelper.getKeyStationClosest() )){
            boolean value = sharedPreferences.getBoolean( key, false );
        }
    }
}
