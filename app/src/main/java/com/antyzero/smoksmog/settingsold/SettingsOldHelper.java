package com.antyzero.smoksmog.settingsold;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.antyzero.smoksmog.R;

public class SettingsOldHelper {

    private static final String MISSING_STRING_VALUE = "132ad@D@FWEvwefv@$%^";

    private final Context context;
    private final SharedPreferences defaultPreferences;

    public SettingsOldHelper( Context context ) {
        this.context = context.getApplicationContext();

        PreferenceManager.setDefaultValues( this.context, R.xml.settings_old_general, false );
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences( this.context );
    }

    public SharedPreferences getPreferences() {
        return defaultPreferences;
    }

    public long getDefaultStationId() {
        String key = context.getString( R.string.pref_key_station_selected );
        String value = defaultPreferences.getString( key, null );
        return value != null ? Long.valueOf( value ) : -1;
    }

    /**
     * @return
     * @throws Exception
     */
    @NonNull
    public StationSelectionMode getStationSelectionMode() throws Exception {

        String key = context.getString( R.string.pref_key_station_selection_mode );
        String value = defaultPreferences.getString( key, MISSING_STRING_VALUE );

        if ( MISSING_STRING_VALUE.equalsIgnoreCase( value ) ) {
            throw new IllegalStateException( "Missing preference value" );
        }

        return StationSelectionMode.find( context, value );
    }

    @NonNull
    public StationSelectionMode getStationSelectionModeNoException() {
        try {
            return getStationSelectionMode();
        } catch ( Exception e ) {
            e.printStackTrace();
            return StationSelectionMode.UNKNOWN;
        }
    }
}
