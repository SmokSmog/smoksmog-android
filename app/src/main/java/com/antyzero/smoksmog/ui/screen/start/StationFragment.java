package com.antyzero.smoksmog.ui.screen.start;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.BaseFragment;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class StationFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = StationFragment.class.getSimpleName();
    private static final String ARG_STATION_ID = "argStationId";
    public static final int NEAREST_STATION_ID = 0;

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    GoogleApiClient googleApiClient;


    private long stationId;

    private List<Station> stationContainer = new ArrayList<>();

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if ( !getArguments().containsKey( ARG_STATION_ID ) ) {
            throw new IllegalStateException( String.format(
                    "%s should be created with newInstance method, missing ARG_STATION_ID",
                    StationFragment.class.getSimpleName() ) );
        }

        stationId = getArguments().getLong( ARG_STATION_ID );
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
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        FragmentActivity activity = getActivity();
        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new SupportFragmentModule( this ), new GoogleModule( this ) )
                .inject( this );

        googleApiClient.connect();

        if ( stationId > 0 ) {
            smokSmog.getApi().station( stationId )
                    .subscribeOn( Schedulers.newThread() )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribe( StationFragment.this::updateUI,
                            throwable -> {
                                logger.i( TAG, "Unable to load station data (stationID:" + stationId + ")", throwable );
                                errorReporter.report( R.string.error_unable_to_load_station_data, stationId );
                            } );
        }
    }

    /**
     * Update UI with new station data
     *
     * @param station data
     */
    private void updateUI( Station station ) {
        stationContainer.clear();
        stationContainer.add( station );
        recyclerView.getAdapter().notifyDataSetChanged();
        try {
            (( AppCompatActivity) getActivity()).getSupportActionBar().setTitle( station.getName() );
        } catch ( Exception e ){
            // totally ignore this issue
        }
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

    @Override
    public void onConnected( @Nullable Bundle bundle ) {

        if( stationId == NEAREST_STATION_ID ){
            ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider( getActivity() );

            reactiveLocationProvider.getLastKnownLocation()
                    .subscribeOn( Schedulers.newThread() )
                    .flatMap( location -> smokSmog.getApi().stationByLocation( location.getLatitude(), location.getLongitude() ) )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribe( this::updateUI, throwable -> {
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
        return stationId;
    }
}
