package com.antyzero.smoksmog.ui.screen.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.annotation.StringRes;
import android.view.View;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.settings.StationSelectionMode;
import com.antyzero.smoksmog.ui.BasePreferenceFragment;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.crashlytics.android.answers.Answers;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.List;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;

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

    private ListPreference stationSelected;
    private ListPreference stationMode;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        addPreferencesFromResource( R.xml.settings_general );
        stationSelected = ( ListPreference ) findPreference( R.string.pref_key_station_selected );
        stationMode = ( ListPreference ) findPreference( R.string.pref_key_station_selection_mode );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ) )
                .inject( this );
        settingsHelper.getPreferences().registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        smokSmog.getApi().stations()
                .compose( RxLifecycle.bindFragment( lifecycle() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        stations -> {
                            stationSelected.setEntries( buildEntries( stations ) );
                            stationSelected.setEntryValues( buildValues( stations ) );

                            try {
                                stationSelected.setEnabled( StationSelectionMode.DEFINED.equals(
                                        settingsHelper.getStationSelectionMode() ) );

                                long stationId = settingsHelper.getDefaultStationId();

                                if ( stationId > 0 ) {
                                    String value = String.valueOf( stationId );
                                    stationSelected.setValue( value );
                                    stationSelected.setSummary( stationSelected.getEntries()[stationSelected.findIndexOfValue( value )] );
                                } else {
                                    stationSelected.setSummary( getActivity().getString( R.string.pref_station_selected_summary_default ) );
                                }

                            } catch ( Exception e ) {
                                logger.e( TAG, "Unable to update status of selected station option", e );
                                // TODO error report
                            }
                        },
                        throwable -> {
                            logger.i( TAG, "Problem with loading stations for settings", throwable );
                            // TODO error report
                        } );
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        stationMode.setSummary( stationMode.getEntry() );

        stationMode.setOnPreferenceChangeListener( ( preference, newValue ) -> {
            try {
                String value = String.valueOf( newValue );
                boolean isDefined = StationSelectionMode.DEFINED.equals( StationSelectionMode.find( getActivity(), value ) );
                stationSelected.setEnabled( stationSelected.getEntries() != null && stationSelected.getEntries().length > 0 && isDefined );
            } catch ( Exception e ) {
                logger.e( TAG, "Problem with enabling preference StationSelected, passed new value [" + newValue + "]", e );
                errorReporter.report( R.string.error_unable_to_enable_station_preference );
                return false;
            }
            return true;
        } );
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {

        Preference preference = findPreference( key );

        if ( preference instanceof ListPreference ) {
            ListPreference listPreference = ( ListPreference ) preference;
            listPreference.setSummary( listPreference.getEntry() );
        }

    }

    @Override
    public void onDetach() {
        settingsHelper.getPreferences().unregisterOnSharedPreferenceChangeListener( this );
        super.onDetach();
    }

    private static CharSequence[] buildEntries( List<Station> stations ) {
        CharSequence[] sequences = new CharSequence[stations.size()];
        for ( int i = 0; i < stations.size(); i++ ) {
            sequences[i] = stations.get( i ).getName();
        }
        return sequences;
    }

    private static CharSequence[] buildValues( List<Station> stations ) {
        CharSequence[] sequences = new CharSequence[stations.size()];
        for ( int i = 0; i < stations.size(); i++ ) {
            sequences[i] = String.valueOf( stations.get( i ).getId() );
        }
        return sequences;
    }

    protected Preference findPreference( @StringRes int stringResId ) {
        return findPreference( getString( stringResId ) );
    }

    public static GeneralSettingsFragment create() {
        return new GeneralSettingsFragment();
    }
}
