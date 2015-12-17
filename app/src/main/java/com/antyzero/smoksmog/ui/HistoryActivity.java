package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.antyzero.smoksmog.R;

import pl.malopolska.smoksmog.model.Station;

/**
 * Shows history chart
 */
public class HistoryActivity extends BaseActivity {
    private static final String STATION_KEY = "station_key";

    public static Intent createIntent(final Context context, final Station station) {
        final Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(STATION_KEY, station);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }
}
