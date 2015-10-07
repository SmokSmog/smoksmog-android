package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemSelected;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "MainActivity";

    @Inject
    Api api;
    @Inject
    GoogleApiClient googleApiClient;

    @Bind( R.id.toolbar )
    Toolbar toolbar;
    @Bind( R.id.spinnerStations )
    Spinner spinnerStations;

    private final List<Station> stations = new ArrayList<>();
    @SuppressWarnings( "FieldCanBeLocal" )
    private ArrayAdapter<Station> adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setSupportActionBar( toolbar );
        setTitle( null );

        SmokSmogApplication.get( this ).getAppComponent()
                .plus( new ActivityModule( this ) ).inject( this );

        adapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, stations );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        spinnerStations.setAdapter( adapter );

        api.stations()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this.stations::addAll,
                        throwable -> Log.e( TAG, "Unable to load stations", throwable ),
                        adapter::notifyDataSetChanged );

        googleApiClient.connect();
    }

    @OnItemSelected( value = R.id.spinnerStations )
    void OnSpinnerSelected( int position ){
        api.station( stations.get( position ).getId() );
    }

    private void updateWithStation(Station station){
        // TODO UI update here
    }

    @Override
    public void onConnected( Bundle bundle ) {

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .concatMap( location -> api.stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .subscribe(
                        station -> System.out.println( station.getName() ),
                        throwable -> Log.e( TAG, "Error on station acquired", throwable ),
                        () -> System.out.println( "Location acquired" ) );
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }
}
