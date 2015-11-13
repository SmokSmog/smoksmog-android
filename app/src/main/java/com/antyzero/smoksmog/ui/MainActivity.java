package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.RxJava;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemSelected;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.ApiUtils;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "MainActivity";

    @Inject
    SmokSmog smokSmog;
    @Inject
    GoogleApiClient googleApiClient;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Logger logger;

    @Bind( R.id.toolbar )
    Toolbar toolbar;
    @Bind( R.id.spinnerStations )
    Spinner spinnerStations;
    @Bind( R.id.indicatorMain )
    IndicatorView indicatorMain;
    @Bind( R.id.textViewName )
    TextView textViewName;
    @Bind( R.id.recyclerViewParticulates )
    RecyclerView recyclerViewParticulates;

    private final List<Station> stations = new ArrayList<>();
    private final List<Particulate> particulates = new ArrayList<>();
    @SuppressWarnings( "FieldCanBeLocal" )
    private ArrayAdapter<Station> adapterStations;

    private Subscription spinnerSubscriber = RxJava.EMPTY_SUBSCRIPTION;
    private ParticulateAdapter particulateAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setSupportActionBar( toolbar );
        setTitle( null );

        SmokSmogApplication.get( this ).getAppComponent().plus( new ActivityModule( this ) ).inject( this );

        particulateAdapter = new ParticulateAdapter( particulates );
        recyclerViewParticulates.setLayoutManager( new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false ) );
        recyclerViewParticulates.setAdapter( particulateAdapter );

        adapterStations = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, stations );
        adapterStations.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        spinnerStations.setAdapter( adapterStations );

        smokSmog.getApi().stations()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this.stations::addAll,
                        throwable -> {
                            String errorMessage = getString( R.string.error_unable_to_load_stations );
                            errorReporter.report( errorMessage );
                            logger.e( TAG, errorMessage, throwable );
                        },
                        adapterStations::notifyDataSetChanged );

        googleApiClient.connect();
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
                                        // TODO pick closes location
                                    },
                                    throwable -> {
                                        // Unable to get local data
                                    }
                            );
                }

                break;
            default:
                return super.onOptionsItemSelected( item );
        }
        return true;
    }

    @OnItemSelected( value = R.id.spinnerStations )
    void OnSpinnerSelected( int position ) {
        Station stationSelected = stations.get( position );

        spinnerSubscriber.unsubscribe();
        spinnerSubscriber = smokSmog.getApi().station( stationSelected.getId() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        station -> {
                            textViewName.setText( station.getName() );
                            updateUiWithStation( station );
                        },
                        throwable -> {
                            errorReporter.report( R.string.error_unable_to_load_station_data, stationSelected.getName() );
                            logger.w( TAG, "Unable to load data for selected station: " + stationSelected.getName(), throwable );
                        }
                );
    }

    /**
     * Update element of the UI with Station object data
     *
     * @param station data
     */
    private void updateUiWithStation( Station station ) {

        List<Particulate> sorted = ApiUtils.sortParticulates( station.getParticulates() )
                .toList().toBlocking().first();

        particulates.clear();
        particulates.addAll( sorted );
        particulateAdapter.notifyDataSetChanged();

        updateUiWithMainParticulate( sorted.get( 0 ) );
    }

    /**
     * Updates information about given particulate
     *
     * @param particulate
     */
    private void updateUiWithMainParticulate( Particulate particulate ) {
        indicatorMain.setParticulate( particulate );
    }

    @Override
    public void onConnected( Bundle bundle ) {

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .concatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this::updateUiWithStation,
                        throwable -> {
                            errorReporter.report( R.string.error_no_near_Station );
                            logger.w( TAG, "Unable to find nearest station data", throwable );
                        }
                );
    }

    @Override
    public void onConnectionSuspended( int i ) {
        // GoogleClient
    }
}
