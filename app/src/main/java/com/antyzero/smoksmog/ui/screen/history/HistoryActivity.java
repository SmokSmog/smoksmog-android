package com.antyzero.smoksmog.ui.screen.history;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.ActivityModule;
import com.antyzero.smoksmog.ui.BaseActivity;
import com.trello.rxlifecycle.RxLifecycle;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Shows history chart
 */
public class HistoryActivity extends BaseActivity {
    private static final String STATION_ID_KEY = "station_id_key";
    private static final String TAG = "HistoryActivity";

    @Inject
    SmokSmog smokSmog;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Logger logger;

    @Bind( R.id.toolbar )
    Toolbar toolbar;

    @Bind( R.id.recyclerViewCharts )
    RecyclerView chartsRecyclerView;

    public static Intent createIntent( final Context context, Station station ) throws Exception {
        if ( station == null ) {
            throw new IllegalArgumentException( Station.class.getSimpleName() + " cannot be null" );
        }
        final Intent intent = new Intent( context, HistoryActivity.class );
        intent.putExtra( STATION_ID_KEY, station.getId() );
        return intent;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        SmokSmogApplication.get( this ).getAppComponent().plus( new ActivityModule( this ) ).inject( this );

        final long stationId = getStationIdExtra( getIntent() );

        setContentView( R.layout.activity_history );
        setSupportActionBar( toolbar );
        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        smokSmog.getApi().stationHistory( stationId )
                .compose( bindToLifecycle() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        this::showHistory,
                        throwable -> {
                            String message = getString( R.string.error_unable_to_load_station_history );
                            errorReporter.report( message );
                            logger.w( TAG, message, throwable );
                        } );
    }

    private void showHistory( Station station ) {
        if ( station == null || station.getParticulates() == null ) {
            //no valid data
            return;
        }
        final int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
        final HistoryAdapter adapter = new HistoryAdapter( station.getParticulates() );
        chartsRecyclerView.setLayoutManager( new GridLayoutManager( this, spanCount, LinearLayoutManager.VERTICAL, false ) );
        chartsRecyclerView.setAdapter( adapter );
    }

    /**
     * @return Station ID if available or throws a {@link IllegalArgumentException}
     */
    private long getStationIdExtra( final Intent intent ) {
        if ( intent == null || !intent.hasExtra( STATION_ID_KEY ) ) {
            Toast.makeText( this, "Pokazanie historii było niemożliwe", Toast.LENGTH_SHORT ).show();
            logger.e( TAG, "Unable to display History screen, missing start data" );
            finish();
            return -1;
        }
        return intent.getLongExtra( STATION_ID_KEY, -1 );
    }
}