package pl.malopolska.smoksmog.toolbar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.BuildConfig;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.StationLocation;

public class ToolbarController {

    private final Toolbar toolbar;
    private final ActionBar actionBar;
    private final StationAdapter stationAdapter;

    private final List<StationLocation> stationList = new ArrayList<>();

    @InjectView(R.id.spinner)
    Spinner spinner;

    @Inject
    SmokSmogAPI smokSmogAPI;

    public ToolbarController(@NonNull final BaseActivity activity, @NonNull Toolbar toolbar) {

        Dagger_ToolbarComponent.builder()
                .activityComponent(activity.getActivityComponent())
                .build().inject(this);

        ButterKnife.inject(this, toolbar);

        Toast.makeText(activity, ">> " + spinner, Toast.LENGTH_SHORT).show();

        this.toolbar = toolbar;

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        if (BuildConfig.DEBUG) {
            toolbar.setBackgroundColor(Color.rgb(234, 42, 120));
        }

        stationAdapter = new StationAdapter(activity, stationList);
        spinner.setAdapter(stationAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Collection<StationLocation> stations = smokSmogAPI.stations();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stationList.clear();
                        stationList.addAll(stations);

                        stationAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
