package com.antyzero.smoksmog.ui.screen.settings;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.support.annotation.NonNull;
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

    private static final int REQUEST_CODE_ASK_PERMISSION = 123;

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

    private String keyStationClosest;
    private CheckBoxPreference preferenceStationCloset;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        addPreferencesFromResource( R.xml.settings_general );
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
        keyStationClosest = getString( R.string.pref_key_station_closest );
        preferenceStationCloset = ( CheckBoxPreference ) findPreference( keyStationClosest );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ) )
                .inject( this );
    }

    @Override
    public void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
        super.onDestroy();
    }

    protected Preference findPreference( @StringRes int stringResId ) {
        return findPreference( getString( stringResId ) );
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {

        // Check permission - preference if changed
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            String accessCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
            boolean isClosestStationKey = keyStationClosest.equals( key );
            boolean hasLocationPermission = getActivity().checkSelfPermission( accessCoarseLocation ) != PackageManager.PERMISSION_GRANTED;
            if ( isClosestStationKey && hasLocationPermission ) {
                requestPermissions( new String[]{ accessCoarseLocation }, REQUEST_CODE_ASK_PERMISSION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        switch ( requestCode ) {
            case REQUEST_CODE_ASK_PERMISSION:
                // if not granted keep closest station not visible
                if ( grantResults[0] != PackageManager.PERMISSION_GRANTED ) {
                    SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
                    sharedPreferences.unregisterOnSharedPreferenceChangeListener( this );
                    preferenceStationCloset.setChecked( false );
                    sharedPreferences.registerOnSharedPreferenceChangeListener( this );
                    // TODO localize
                    errorReporter.report( "Location permission not granted" );
                }
                break;
            default:
                super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        }
    }

    public static GeneralSettingsFragment create() {
        return new GeneralSettingsFragment();
    }
}
