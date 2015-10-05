package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trello.rxlifecycle.RxLifecycle;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.Api;
import rx.android.schedulers.AndroidSchedulers;
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

        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( this );

        reactiveLocationProvider.getLastKnownLocation()
                .doOnError( ( throwable ) -> System.out.println( "Error on station acquired, " + throwable ) )
                .compose( RxLifecycle.bindActivity( lifecycle() ) )
                .subscribeOn( Schedulers.newThread() )
                .concatMap( location -> api.stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnNext( station -> System.out.println( station.getName() ) )
                .doOnCompleted( () -> System.out.println( "Location accuired" ) )
                .subscribe();
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }
}
