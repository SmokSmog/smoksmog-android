package com.antyzero.smoksmog.ui;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.trello.rxlifecycle.RxLifecycle;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks {

    @Inject
    Api api;
    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        SmokSmogApplication.get( this ).getAppComponent()
                .plus( new ActivityModule( this ) ).inject( this );

        googleApiClient.connect();
    }

    @Override
    public void onConnected( Bundle bundle ) {

        Location location = LocationServices.FusedLocationApi.getLastLocation( googleApiClient );

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .doOnError( new Action1<Throwable>() {
                    @Override
                    public void call( Throwable throwable ) {
                        Toast.makeText( MainActivity.this, "Unable to find nearest station", Toast.LENGTH_SHORT )
                                .show();
                    }
                } )
                .compose( RxLifecycle.bindActivity( lifecycle() ) ).cast( Location.class )
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .concatMap( new Func1<Location, Observable<Station>>() {
                    @Override
                    public Observable<Station> call( Location location ) {
                        Toast.makeText( MainActivity.this, "" + location, Toast.LENGTH_SHORT ).show();
                        return api.stationByLocation( location.getLatitude(), location.getLongitude() );
                    }
                } )
                .doOnNext( new Action1<Station>() {
                    @Override
                    public void call( Station station ) {
                        Toast.makeText( MainActivity.this, station.getName(), Toast.LENGTH_LONG ).show();
                    }
                } )
                .doOnCompleted( new Action0() {
                    @Override
                    public void call() {
                        Toast.makeText( MainActivity.this, "Location accuired", Toast.LENGTH_SHORT ).show();
                    }
                } )
                .subscribe();
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }
}
