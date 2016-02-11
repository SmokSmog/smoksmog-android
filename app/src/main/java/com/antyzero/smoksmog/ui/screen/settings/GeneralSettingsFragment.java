package com.antyzero.smoksmog.ui.screen.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.annotation.StringRes;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.BasePreferenceFragment;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.crashlytics.android.answers.Answers;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmog;

/**
 *
 */
public class GeneralSettingsFragment extends BasePreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = GeneralSettingsFragment.class.getSimpleName();

    //<editor-fold desc="Dagger">
    @Inject
    SmokSmog smokSmog;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Logger logger;
    @Inject
    Answers answers;
    @Inject
    SettingsHelper settingsHelper;
    //</editor-fold>

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        addPreferencesFromResource( R.xml.settings_general );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ) )
                .inject( this );
    }

    protected Preference findPreference( @StringRes int stringResId ) {
        return findPreference( getString( stringResId ) );
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        // dynamic charges
    }

    public static GeneralSettingsFragment create() {
        return new GeneralSettingsFragment();
    }
}
