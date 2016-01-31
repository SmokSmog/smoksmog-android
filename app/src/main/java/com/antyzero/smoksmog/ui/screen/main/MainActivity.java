package com.antyzero.smoksmog.ui.screen.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.RxJava;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.fabric.StationShowEvent;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.BaseActivity;
import com.antyzero.smoksmog.ui.IndicatorView;
import com.antyzero.smoksmog.ui.ParticulateAdapter;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.about.AboutActivity;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity;
import com.crashlytics.android.answers.Answers;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;

import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.ApiUtils;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;
import pl.malopolska.smoksmog.utils.StationUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, ParticulateAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static final String KEY_STATION_ID = "KEY_STATION_ID";

    //<editor-fold desc="Dagger">
    @Inject
    SmokSmog smokSmog;
    @Inject
    GoogleApiClient googleApiClient;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Logger logger;
    @Inject
    Answers answers;
    @Inject
    SettingsHelper settingsHelper;
    //</editor-fold>

    //<editor-fold desc="ViewBindings">
    @Bind( R.id.toolbar )
    Toolbar toolbar;
    @Bind( R.id.spinnerStations )
    Spinner spinnerStations;
    @Bind( R.id.indicatorMain )
    IndicatorView indicatorMain;
    @Bind( R.id.textViewName )
    TextView textViewName;
    @Bind( R.id.textViewConcentration )
    TextView textViewConcentration;
    @Bind( R.id.textViewAverage )
    TextView textViewAverage;
    @Bind( R.id.textViewData )
    TextView textViewData;
    @Bind( R.id.recyclerViewParticulates )
    RecyclerView recyclerViewParticulates;
    @Bind( R.id.buttonHistory )
    View buttonHistory;
    //</editor-fold>

    private final List<Station> stations = new ArrayList<>();
    private final List<Particulate> particulates = new ArrayList<>();

    private ArrayAdapter<Station> adapterStations;
    private Subscription spinnerSubscriber = RxJava.EMPTY_SUBSCRIPTION;
    private ParticulateAdapter particulateAdapter;
    private Station currentStation;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        SmokSmogApplication.get( this ).getAppComponent().plus( new ActivityModule( this ), new GoogleModule( this ) ).inject( this );

        setContentView( R.layout.activity_main );
        setSupportActionBar( toolbar );
        setTitle( null );
        spinnerStations.setEnabled( false );

        particulateAdapter = new ParticulateAdapter( particulates, this );

        int orientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL;

        recyclerViewParticulates.setLayoutManager( new LinearLayoutManager( this, orientation, false ) );
        recyclerViewParticulates.setAdapter( particulateAdapter );

        adapterStations = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, stations );
        adapterStations.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        spinnerStations.setAdapter( adapterStations );

        smokSmog.getApi().stations()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        stations1 -> {
                            this.stations.addAll( stations1 );
                            adapterStations.notifyDataSetChanged();
                            if ( currentStation != null ) {
                                for ( int i = 0; i < stations.size(); i++ ) {
                                    if ( stations.get( i ).getId() == currentStation.getId() ) {
                                        spinnerStations.setSelection( i, false );
                                        break;
                                    }
                                }
                            } else {
                                spinnerStations.setSelection( 0, false );
                            }
                            spinnerStations.setEnabled( true );
                            spinnerStations.setOnItemSelectedListener( this );
                        },
                        throwable -> {
                            Toast.makeText( MainActivity.this, R.string.error_unable_to_load_stations, Toast.LENGTH_SHORT ).show();
                            logger.i( TAG, "Unable to load stations list", throwable );
                            MainActivity.this.finish();
                        } );

        googleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();

        switch ( settingsHelper.getStationSelectionModeNoException() ) {

            case LAST:
                long stationId = loadStationPick();
                smokSmog.getApi().station( stationId )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe(
                                this::updateUiWithStation,
                                throwable -> {
                                    logger.i( TAG, "Unable to load last picked station (id:" + stationId+ ")" );
                                }
                        );
                break;
            case CLOSEST:
                loadDataForCurrentLocation();
                break;
            case DEFINED:
                final long defaultStationId = settingsHelper.getDefaultStationId();
                smokSmog.getApi().station( defaultStationId )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe(
                                this::updateUiWithStation,
                                throwable -> {
                                    logger.i( TAG, "Unable to load defined station (id:" + defaultStationId + ")" );
                                }
                        );
                break;
            default:
                logger.e( TAG, "Missing selection mode value for station loading" );
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {

            case R.id.action_my_location:
                if ( googleApiClient.isConnected() ) {

                    ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

                    reactiveLocationProvider.getLastKnownLocation()
                            .observeOn( AndroidSchedulers.mainThread() )
                            .subscribe(
                                    location -> {
                                        updateUiSpinnerSelectionWithStation(
                                                StationUtils.findClosest( stations,
                                                        location.getLatitude(), location.getLongitude() ) );
                                    },
                                    throwable -> {
                                        errorReporter.report( R.string.error_no_near_Station );
                                        logger.i( TAG, "Unable to find nearest station data", throwable );
                                    }
                            );
                }

                break;

            case R.id.action_settings:
                SettingsActivity.start( this );
                break;

            case R.id.action_about:
                AboutActivity.start( this );
                break;

            default:
                return super.onOptionsItemSelected( item );
        }
        return true;
    }

    /**
     * Called when user want to manually pick up station
     *
     * @param position data
     */
    @Override
    public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
        Station stationSelected = stations.get( position );

        spinnerSubscriber.unsubscribe();
        spinnerSubscriber = smokSmog.getApi().station( stationSelected.getId() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this::updateUiWithStation,
                        throwable -> {
                            errorReporter.report( R.string.error_unable_to_load_station_data, stationSelected.getName() );
                            logger.i( TAG, "Unable to load data for selected station: " + stationSelected.getName(), throwable );
                        }
                );
    }

    @Override
    public void onNothingSelected( AdapterView<?> parent ) {
        // do nothing
    }

    /**
     * Called when particulate is clicked on horizontal scroll
     *
     * @param particulate data
     */
    @Override
    public void onItemClick( Particulate particulate ) {
        updateUiWithMainParticulate( particulate );
    }

    /**
     * Update element of the UI with Station object data
     *
     * @param station data
     */
    private void updateUiWithStation( Station station ) {

        saveStationPick( station );

        currentStation = station;

        answers.logContentView( StationShowEvent.create( station ) );

        textViewName.setText( station.getName() );

        updateUiSpinnerSelectionWithStation( station );

        buttonHistory.setEnabled( true );

        if ( !station.getParticulates().isEmpty() ) {
            List<Particulate> sorted = ApiUtils.sortParticulates( station.getParticulates() )
                    .toList().toBlocking().first();

            particulates.clear();
            particulates.addAll( sorted );
            particulateAdapter.notifyDataSetChanged();

            updateUiWithMainParticulate( sorted.get( 0 ) );
        }
    }

    /**
     * Set selected spinner station to given value (only if within adapter data)
     *
     * @param station data
     */
    private void updateUiSpinnerSelectionWithStation( Station station ) {
        if ( station == null ) {
            return;
        }
        spinnerStations.setSelection( adapterStations.getPosition( station ), false );
    }

    /**
     * Updates information about given particulate
     *
     * @param particulate data
     */
    private void updateUiWithMainParticulate( Particulate particulate ) {
        indicatorMain.setValue( particulate.getAverage() / particulate.getNorm() );

        textViewConcentration.setText( String.format( "%s %s", particulate.getValue(), particulate.getUnit() ) );
        textViewAverage.setText( String.format( "%s %s", particulate.getAverage(), particulate.getUnit() ) );
        textViewData.setText( DateTimeFormat.longDateTime().print( particulate.getDate() ) );
    }

    @Override
    public void onConnected( Bundle bundle ) {
        // TODO wait
    }

    public void saveStationPick( Station station ) {
        getSharedPreferences( "stationPick", MODE_PRIVATE ).edit().putLong( KEY_STATION_ID, station.getId() ).apply();
    }

    public long loadStationPick() {
        return getSharedPreferences( "stationPick", MODE_PRIVATE ).getLong( KEY_STATION_ID, 13 );
    }

    /**
     * Check current location and load data to UI
     */
    private void loadDataForCurrentLocation() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .compose( RxLifecycle.bindUntilActivityEvent( lifecycle(), ActivityEvent.STOP ) )
                .concatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this::updateUiWithStation,
                        throwable -> {
                            logger.i( TAG, "Unable to find nearest station data", throwable );
                            errorReporter.report( R.string.error_no_near_Station );
                        }
                );
    }

    @OnClick( R.id.buttonHistory )
    void onHistoryButtonClick() {
        try {
            startActivity( HistoryActivity.intent( this, currentStation ) );
        } catch ( Exception e ) {
            String message = getString( R.string.error_unable_to_show_history );
            logger.e( TAG, message, e );
            errorReporter.report( message );
        }
    }

    @Override
    public void onConnectionSuspended( int i ) {
        // Do nothing
    }
}
