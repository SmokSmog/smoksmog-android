package com.antyzero.smoksmog.ui.screen.start;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.BaseFragment;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class StationFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, TitleProvider {

    private static final String TAG = StationFragment.class.getSimpleName();
    private static final String ARG_STATION_ID = "argStationId";
    private static final String STATE_STATION_NAME = "Statename";

    public static final int NEAREST_STATION_ID = 0;

    @Bind( R.id.viewSwitcher )
    ViewSwitcher viewSwitcher;
    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;
    @Bind( R.id.progressBar )
    ProgressBar progressBar;

    @Inject
    RxBus rxBus;
    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    GoogleApiClient googleApiClient;

    private List<Station> stationContainer = new ArrayList<>();

    private Station station;
    private Location locationCurrent;
    private Location locationStation;
    private float distanceKilometers;

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if ( !getArguments().containsKey( ARG_STATION_ID ) ) {
            throw new IllegalStateException( String.format(
                    "%s should be created with newInstance method, missing ARG_STATION_ID",
                    StationFragment.class.getSimpleName() ) );
        }
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_station, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity(), VERTICAL, false ) );
        recyclerView.setAdapter( new StationAdapter( stationContainer ) );

        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor( R.color.accent ),
                PorterDuff.Mode.SRC_IN );

        showLoading();
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        Activity activity = getActivity();
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new FragmentModule( this ), new GoogleModule( this ) )
                .inject( this );

        googleApiClient.connect();

        if ( getStationId() > 0 ) {
            smokSmog.getApi().station( getStationId() )
                    .subscribeOn( Schedulers.newThread() )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribe(
                            this::updateUI,
                            throwable -> {
                                logger.i( TAG, "Unable to load station data (stationID:" + getStationId() + ")", throwable );
                                errorReporter.report( R.string.error_unable_to_load_station_data, getStationId() );
                            } );
        }
    }

    @Override
    public String getTitle() {
        return station == null ? null : station.getName();
    }

    @Override
    public String getSubtitle() {
        String subtitle = getString( R.string.station_closest );
        if ( distanceKilometers > 0 ) {
            subtitle += " (" + distanceKilometers + " km)";
        }
        return getStationId() == NEAREST_STATION_ID ? subtitle : null;
    }

    private void showLoading() {
        runOnUiThread( () -> viewSwitcher.setDisplayedChild( 1 ) );
    }

    private void showData() {
        runOnUiThread( () -> viewSwitcher.setDisplayedChild( 0 ) );
    }

    private void runOnUiThread( Runnable runnable ) {
        new Handler( Looper.getMainLooper() ).post( runnable );
    }

    /**
     * Update UI with new station data
     *
     * @param station data
     */
    private void updateUI( Station station ) {

        this.station = station;

        stationContainer.clear();
        stationContainer.add( station );
        recyclerView.getAdapter().notifyDataSetChanged();

        rxBus.send( new StartActivity.TitleUpdateEvent() );

        if ( locationCurrent != null && locationStation != null ) {
            distanceKilometers = locationCurrent.distanceTo( locationStation ) / 1000;
        }

        showData();
    }

    @Override
    public void onConnected( @Nullable Bundle bundle ) {

        if ( getStationId() == NEAREST_STATION_ID ) {
            ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( getActivity() );

            LocationRequest request = LocationRequest.create()
                    .setPriority( LocationRequest.PRIORITY_LOW_POWER )
                    .setNumUpdates( 3 )
                    .setInterval( 100L );

            reactiveLocationProvider
                    .getUpdatedLocation( request )
                    .subscribeOn( Schedulers.newThread() )
                    .doOnSubscribe( () -> StationFragment.this.locationCurrent = null )
                    .doOnNext( location -> StationFragment.this.locationCurrent = location )
                    .flatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                    .doOnNext( givenStation -> smokSmog.getApi().stations()
                            .concatMap( Observable::from )
                            .filter( station -> station.getId() == givenStation.getId() )
                            .subscribe( station1 -> {
                                locationStation = new Location( locationCurrent );
                                locationStation.setLongitude( station1.getLongitude() );
                                locationStation.setLatitude( station1.getLatitude() );
                            } ) )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribe(
                            station -> runOnUiThread( () -> updateUI( station ) ),
                            throwable -> {
                                logger.i( TAG, "Unable to find closes station", throwable );
                                errorReporter.report( R.string.error_no_near_Station );
                            } );
        }
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }

    /**
     * Get station Id used to create this fragment
     *
     * @return station ID
     */
    public long getStationId() {
        return getArguments().getLong( ARG_STATION_ID );
    }

    /**
     * Proper way to create fragment
     *
     * @param stationId for data download
     * @return StationFragment instance
     */
    public static StationFragment newInstance( long stationId ) {

        Bundle arguments = new Bundle();

        arguments.putLong( ARG_STATION_ID, stationId );

        StationFragment stationFragment = new StationFragment();
        stationFragment.setArguments( arguments );

        return stationFragment;
    }
}
