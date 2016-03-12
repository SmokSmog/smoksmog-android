package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationStationFragment extends StationFragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = LocationStationFragment.class.getSimpleName();

    private static final long LOCATION_TIMEOUT_IN_SECONDS = 4;

    private Location locationCurrent;
    private Location locationStation;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        Activity activity = getActivity();
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ), new GoogleModule( this ) )
                .inject( this );

        googleApiClient.connect();
    }

    @Override
    public void onConnected( @Nullable Bundle bundle ) {
        loadData();
    }

    @Override
    protected void loadData() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( getActivity() );

        LocationRequest request = LocationRequest.create()
                .setPriority( LocationRequest.PRIORITY_LOW_POWER )
                .setNumUpdates( 1 )
                .setExpirationDuration( TimeUnit.SECONDS.toMillis( LOCATION_TIMEOUT_IN_SECONDS ) );

        reactiveLocationProvider.getUpdatedLocation( request )
                .compose( bindUntilEvent( FragmentEvent.DESTROY_VIEW ) )
                .subscribeOn( Schedulers.newThread() )
                .doOnSubscribe( () -> {
                    LocationStationFragment.this.locationCurrent = null;
                    showLoading();
                } )
                .timeout( LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS )
                .first()
                .doOnNext( location -> LocationStationFragment.this.locationCurrent = location )
                .flatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                .doOnNext( givenStation -> smokSmog.getApi().stations()
                        .compose( bindUntilEvent( FragmentEvent.DESTROY ) )
                        .subscribeOn( Schedulers.newThread() )
                        .concatMap( Observable::from )
                        .filter( station -> station.getId() == givenStation.getId() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe( station1 -> {
                            locationStation = new Location( locationCurrent );
                            locationStation.setLongitude( station1.getLongitude() );
                            locationStation.setLatitude( station1.getLatitude() );
                        } ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        station -> updateViewsOnUiThread( () -> updateUI( station ) ),
                        throwable -> {
                            try {
                                showTryAgain( R.string.error_no_near_Station );
                            } catch ( Exception e ) {
                                logger.e( TAG, "Problem with error handling code", e );
                            } finally {
                                logger.i( TAG, "Unable to find closes station", throwable );
                                errorReporter.report( R.string.error_no_near_Station );
                            }
                        } );
    }

    @Override
    public void onConnectionSuspended( int i ) {
        // do nothing
    }
}
