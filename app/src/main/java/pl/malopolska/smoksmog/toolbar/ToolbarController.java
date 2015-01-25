package pl.malopolska.smoksmog.toolbar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.BuildConfig;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.network.StationLocation;
import pl.malopolska.smoksmog.network.StationLocationImpl;
import pl.malopolska.smoksmog.ui.ActionBarActivity;

public class ToolbarController {

    @InjectView(R.id.spinner)
    Spinner spinner;

    private final Toolbar toolbar;
    private final ActionBar actionBar;

    public ToolbarController(@NonNull ActionBarActivity activity, @NonNull Toolbar toolbar) {

        this.toolbar = toolbar;

        ButterKnife.inject(this, toolbar);

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        if (BuildConfig.DEBUG) {
            toolbar.setBackgroundColor(Color.rgb(234, 42, 120));
        }

        List<StationLocation> stationList = new ArrayList<>();

        stationList.add(new StationLocationImpl());

        spinner.setAdapter(new StationAdapter( stationList ));
    }
}
