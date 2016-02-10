package com.antyzero.smoksmog.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.antyzero.smoksmog.utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class SettingsHelper {

    private static final String TAG = SettingsHelper.class.getSimpleName();

    private static final String EMPTY_STRING = "awd5ijsadf";
    private static final String SPLIT_CHAR = "%!@";
    private static final String KEY_STATION_ID_LIST = "KEY_STATION_ID_LIST";

    private final SharedPreferences preferences;

    private final Convert stringConvert = new Convert();

    public SettingsHelper( Context context ) {
        preferences = context.getSharedPreferences( TAG, Context.MODE_PRIVATE );
    }

    public List<Long> getStationIdList() {
        return getList( preferences, KEY_STATION_ID_LIST, Long.class );
    }

    public void setStationIdList( List<Long> longList ) {
        preferences.edit().putString( KEY_STATION_ID_LIST, TextUtils.join( SPLIT_CHAR, longList ) ).apply();
    }

    private <T> List<T> getList( SharedPreferences sharedPreferences, String field, Class<T> type ) {
        List<T> result = new ArrayList<>();
        String string = sharedPreferences.getString( field, EMPTY_STRING );
        if( !TextUtils.isEmpty( string ) && !string.equalsIgnoreCase( EMPTY_STRING ) ){
            String[] array = string.split( SPLIT_CHAR );
            for( String item : array ){
                result.add( ( T ) stringConvert.getConverterFor( type ).convert( item ) );
            }
        }
        return result;
    }


}
