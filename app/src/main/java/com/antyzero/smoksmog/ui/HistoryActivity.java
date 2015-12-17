package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;

/**
 * Shows history chart
 */
public class HistoryActivity extends BaseActivity {
    private static final String STATION_ID_KEY = "station_id_key";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.lineChart1)
    LineChart lineChart1;

    public static Intent createIntent(final Context context, final long stationId) {
        final Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(STATION_ID_KEY, stationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }

    private void setupChartView(final LineChart lineChart) {
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
