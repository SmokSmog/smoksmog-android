package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.graphics.PorterDuff;
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
import android.widget.ViewAnimator;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.BaseFragment;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;
import com.antyzero.smoksmog.ui.screen.start.StationAdapter;
import com.antyzero.smoksmog.ui.screen.start.TitleProvider;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public abstract class StationFragment extends BaseFragment implements TitleProvider {

    private static final String ARG_STATION_ID = "argStationId";

    public static final int NEAREST_STATION_ID = 0;

    @Bind( R.id.viewAnimator )
    ViewAnimator viewAnimator;
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

    private List<Station> stationContainer = new ArrayList<>();

    private Station station;

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

    /**
     * Implement for data load
     */
    protected abstract void loadData();

    @Override
    public String getTitle() {
        return station == null ? null : station.getName();
    }

    @Override
    public String getSubtitle() {
        return getStationId() == NEAREST_STATION_ID ? getString( R.string.station_closest ) : null;
    }

    protected void showLoading() {
        runOnUiThread( () -> viewAnimator.setDisplayedChild( 1 ) );
    }

    protected void showData() {
        runOnUiThread( () -> viewAnimator.setDisplayedChild( 0 ) );
    }

    protected void showTryAgain() {
        runOnUiThread( () -> viewAnimator.setDisplayedChild( 2 ) );
    }

    protected void runOnUiThread( Runnable runnable ) {
        new Handler( Looper.getMainLooper() ).post( runnable );
    }

    @OnClick(R.id.buttonTryAgain)
    void buttonReloadData(){
        loadData();
    }

    /**
     * Update UI with new station data
     *
     * @param station data
     */
    protected void updateUI( Station station ) {

        this.station = station;

        stationContainer.clear();
        stationContainer.add( station );
        recyclerView.getAdapter().notifyDataSetChanged();

        rxBus.send( new StartActivity.TitleUpdateEvent() );

        showData();
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

        StationFragment stationFragment = stationId <= 0 ?
                new LocationStationFragment() :
                new NetworkStationFragment();
        stationFragment.setArguments( arguments );

        return stationFragment;
    }
}
