package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemSelected;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, Observer<Station> {

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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setSupportActionBar( toolbar );
        setTitle( null );

        SmokSmogApplication.get( this ).getAppComponent().plus( new ActivityModule( this ) ).inject( this );

        recyclerViewParticulates.setLayoutManager(
                new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false ) );
        recyclerViewParticulates.setAdapter( new ParticulateAdapter( particulates ) );

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

    @OnItemSelected( value = R.id.spinnerStations )
    void OnSpinnerSelected( int position ) {
        Station stationSelected = stations.get( position );

        spinnerSubscriber.unsubscribe();
        spinnerSubscriber = smokSmog.getApi().station( stationSelected.getId() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> {
                    String errorMessage = getString( R.string.error_unable_to_load_station_data, stationSelected.getName() );
                    errorReporter.report( errorMessage );
                    logger.w( TAG, errorMessage, throwable );
                } )
                .doOnNext( station -> textViewName.setText( station.getName() ) )
                .subscribe( this );
    }

    /**
     * Update element of the UI with Station object data
     *
     * @param station data
     */
    private void updateUiWithStation( Station station ) {
        indicatorMain.setParticulate( station.getParticulates().get( 0 ) );
    }

    @Override
    public void onConnected( Bundle bundle ) {

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .concatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( this );
    }

    @Override
    public void onConnectionSuspended( int i ) {
        // GoogleClient
    }

    @Override
    public void onNext( Station station ) {
        updateUiWithStation( station );
    }

    @Override
    public void onCompleted() {
        // do nothing
    }

    @Override
    public void onError( Throwable e ) {
        logger.w( TAG, "Error when loading station data", e );
    }
}
