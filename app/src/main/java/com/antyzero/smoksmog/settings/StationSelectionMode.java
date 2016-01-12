package com.antyzero.smoksmog.settings;

import android.content.Context;
import android.support.annotation.StringRes;

import com.antyzero.smoksmog.R;

public enum StationSelectionMode {

    LAST( R.string.pref_station_default_value_last ),

    CLOSEST( R.string.pref_station_default_value_closest ),

    DEFINED( R.string.pref_station_default_value_defined ),

    UNKNOWN( android.R.string.unknownName );

    private final int prefValueString;

    StationSelectionMode( @StringRes int prefValueString ) {
        this.prefValueString = prefValueString;
    }

    /**
     *
     *
     * @param context
     * @param look
     * @return
     * @throws Exception
     */
    public static StationSelectionMode find( Context context, String look ) throws Exception {

        for ( StationSelectionMode selectionMode : values() ) {
            if ( context.getString( selectionMode.prefValueString ).equalsIgnoreCase( look ) ) {
                return selectionMode;
            }
        }

        throw new IllegalArgumentException( "Unable to find enum value for given string" );
    }
}
