package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.History;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Shows history chart
 */
public class HistoryActivity extends BaseActivity {
    private static final String STATION_ID_KEY = "station_id_key";

    @Inject
    SmokSmog smokSmog;

    @Inject
    ErrorReporter errorReporter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerViewCharts)
    RecyclerView chartsRecyclerView;

    public static Intent createIntent(final Context context, final long stationId) {
        final Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(STATION_ID_KEY, stationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SmokSmogApplication.get(this).getAppComponent().plus(new ActivityModule(this)).inject(this);

        final long stationId = getStationIdExtra(getIntent());

        smokSmog.getApi().stationHistory(stationId)
            .compose(RxLifecycle.bindActivity(lifecycle()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::showHistory, throwable -> errorReporter.report(getString(R.string.error_unable_to_load_station_history)));
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
    private static long getStationIdExtra(final Intent intent) {
        if (intent == null || !intent.hasExtra(STATION_ID_KEY)) {
            throw new IllegalArgumentException();
        }
        return intent.getLongExtra(STATION_ID_KEY, -1);
    }
}