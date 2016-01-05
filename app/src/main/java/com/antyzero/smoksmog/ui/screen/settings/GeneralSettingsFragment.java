package com.antyzero.smoksmog.ui.screen.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.antyzero.smoksmog.R;

/**
 *
 */
public class GeneralSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        addPreferencesFromResource( R.xml.settings_general );
    }

    public static GeneralSettingsFragment create(){
        return new GeneralSettingsFragment();
    }
}
