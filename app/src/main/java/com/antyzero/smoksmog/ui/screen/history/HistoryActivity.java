package com.antyzero.smoksmog.ui.screen.history;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.ui.BaseDragonActivity;
import com.antyzero.smoksmog.ui.screen.ActivityModule;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import smoksmog.logger.Logger;

/**
 * Shows history chart
 */
public class HistoryActivity extends BaseDragonActivity {
    private static final String STATION_ID_KEY = "station_id_key";
    private static final String TAG = "HistoryActivity";

    @Inject
    SmokSmog smokSmog;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Logger logger;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewCharts)
    RecyclerView chartsRecyclerView;

    /**
     * Simple way to start HistoryActivity
     *
     * @param context   for start
     * @param stationId to show
     */
    public static void start(@NonNull Context context, @IntRange(from = 1) long stationId) {
        context.startActivity(intent(context, stationId));
    }

    /**
     * @param context for start
     * @param station to show
     * @return valid Intent to start HistoryActivity
     */
    public static Intent intent(@NonNull final Context context, @NonNull Station station) {
        return intent(context, station.getId());
    }

    /**
     * @param context   for start
     * @param stationId to show
     * @return valid Intent to start HistoryActivity
     */
    public static Intent intent(@NonNull final Context context, @IntRange(from = 1) long stationId) {
        final Intent intent = new Intent(context, HistoryActivity.class);
        fillIntent(intent, stationId);
        return intent;
    }

    @VisibleForTesting
    public static Intent fillIntent(@NonNull Intent intent, @IntRange(from = 1) long stationId) {
        intent.putExtra(STATION_ID_KEY, stationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmokSmogApplication.Companion.get(this).getAppComponent().plus(new ActivityModule(this)).inject(this);

        final long stationId = getStationIdExtra(getIntent());

        setContentView(R.layout.activity_history);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        smokSmog.getApi().stationHistory(stationId)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .cast(Station.class)
                .subscribe(
                        new Action1<Station>() {
                            @Override
                            public void call(Station station) {
                                showHistory(station);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                String message = getString(R.string.error_unable_to_load_station_history);
                                errorReporter.report(message);
                                logger.i(TAG, message, throwable);
                            }
                        });
    }

    private void showHistory(Station station) {
        if (station == null || station.getParticulates() == null) {
            //no valid data
            return;
        }
        final int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
        final HistoryAdapter adapter = new HistoryAdapter(station.getParticulates());
        chartsRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount, LinearLayoutManager.VERTICAL, false));
        chartsRecyclerView.setAdapter(adapter);
    }

    /**
     * @return Station ID if available or throws a {@link IllegalArgumentException}
     */
    private long getStationIdExtra(final Intent intent) {
        if (intent == null || !intent.hasExtra(STATION_ID_KEY)) {
            // TODO toast text should be in resources and tranlsted
            Toast.makeText(this, "Pokazanie historii było niemożliwe", Toast.LENGTH_SHORT).show();
            logger.e(TAG, "Unable to display History screen, missing start data");
            finish();
            return -1;
        }
        return intent.getLongExtra(STATION_ID_KEY, -1);
    }
}