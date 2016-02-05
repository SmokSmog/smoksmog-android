package com.antyzero.smoksmog.ui.screen.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.BaseFragment;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class StationFragment extends BaseFragment {

    private static final String TAG = StationFragment.class.getSimpleName();
    private static final String ARG_STATION_ID = "argStationId";

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;

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
                .plus( new SupportFragmentModule( this ) )
                .inject( this );

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

        // TODO add use case for closest station
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
    }

    /**
     * @param stationId
     * @return
     */
    public static Fragment newInstance( long stationId ) {

        Bundle arguments = new Bundle();

        arguments.putLong( ARG_STATION_ID, stationId );

        StationFragment stationFragment = new StationFragment();
        stationFragment.setArguments( arguments );

        return stationFragment;
    }
}
