package com.antyzero.smoksmog.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.utils.Convert;

import java.util.ArrayList;
import java.util.List;

/**
 * Working on settings helper.
 * <p>
 * This class should be responsible for data manipulation of every user-changeable data
 */
public class SettingsHelper {

    private static final String TAG = SettingsHelper.class.getSimpleName();

    private static final String EMPTY_STRING = "awd5ijsadf";
    private static final String SPLIT_CHAR = "%!@";
    private static final String KEY_STATION_ID_LIST = "KEY_STATION_ID_LIST";

    private final SharedPreferences defaultPreferences;

    private final List<Long> stationIds;

    private final Convert stringConvert = new Convert();
    private final Context context;

    public SettingsHelper( Context context ) {
        this.context = context;
        PreferenceManager.setDefaultValues( context, R.xml.settings_general, false );
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        stationIds = getList( defaultPreferences, KEY_STATION_ID_LIST, Long.class );
    }

    public SharedPreferences getPreferences() {
        return defaultPreferences;
    }

    public void setClosesStationVisible( boolean value ) {
        defaultPreferences.edit().putBoolean(
                context.getString( R.string.pref_key_station_closest ), value ).apply();
    }

    /**
     * Get info is closes station should be visible on main screen
     *
     * @return boolean value
     */
    public boolean isClosesStationVisible() {
        return defaultPreferences.getBoolean(
                context.getString( R.string.pref_key_station_closest ), true );
    }

    /**
     * Get current station id list
     *
     * @return station id list
     */
    public List<Long> getStationIdList() {
        return stationIds;
    }

    /**
     * Update station list
     *
     * @param longList
     */
    public void setStationIdList( List<Long> longList ) {
        List<Long> longsTemp = new ArrayList<>( longList );
        stationIds.clear();
        stationIds.addAll( longsTemp );
        defaultPreferences.edit().putString( KEY_STATION_ID_LIST, TextUtils.join( SPLIT_CHAR, longList ) ).apply();
    }

    /**
     * Converts string into list of objects of requested type
     *
     * @param sharedPreferences for data
     * @param key               which to get data from
     * @param type              of list
     * @param <T>               generic for list
     * @return list of requested type
     */
    @NonNull
    private <T> List<T> getList( SharedPreferences sharedPreferences, String key, Class<T> type ) {
        List<T> result = new ArrayList<>();
        String string = sharedPreferences.getString( key, EMPTY_STRING );
        if ( !TextUtils.isEmpty( string ) && !string.equalsIgnoreCase( EMPTY_STRING ) ) {
            String[] array = string.split( SPLIT_CHAR );
            for ( String item : array ) {
                result.add( (T) stringConvert.getConverterFor( type ).convert( item ) );
            }
        }
        return result;
    }
}
