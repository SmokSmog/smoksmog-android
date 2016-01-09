package com.antyzero.smoksmog.ui.screen.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.StringRes;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;

import java.util.Arrays;

/**
 *
 */
public class GeneralSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        addPreferencesFromResource( R.xml.settings_general );
        updateSummaryWithEntry( (ListPreference) findPreference( R.string.pref_key_station_default ) );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ) );
    }

    /**
     *
     *
     * @param preference
     */
    private void updateSummaryWithEntry( ListPreference preference ) {
        preference.setSummary( preference.getEntry() );
        preference.setOnPreferenceChangeListener( ( preference1, newValue ) -> {
            int index = Arrays.asList( preference.getEntryValues() ).indexOf( newValue );
            preference.setSummary( preference.getEntries()[index] );
            return true;
        });
    }

    protected Preference findPreference( @StringRes int stringResId ){
        return findPreference( getString( stringResId ) );
    }

    public static GeneralSettingsFragment create(){
        return new GeneralSettingsFragment();
    }
}
