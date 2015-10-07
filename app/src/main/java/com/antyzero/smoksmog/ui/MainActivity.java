package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks {

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

        adapter = new StationSpinnerAdapter( this, stations );

        spinnerStations.setAdapter( adapter );

        api.stations()
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .doOnNext( this.stations::addAll )
                .doOnCompleted( adapter::notifyDataSetChanged )
                .subscribe();

        googleApiClient.connect();
    }

    @Override
    public void onConnected( Bundle bundle ) {

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .doOnError( ( throwable ) -> System.out.println( "Error on station acquired, " + throwable ) )
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .concatMap( location -> api.stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .doOnNext( station -> System.out.println( station.getName() ) )
                .doOnCompleted( () -> System.out.println( "Location acquired" ) )
                .subscribe();
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }
}
