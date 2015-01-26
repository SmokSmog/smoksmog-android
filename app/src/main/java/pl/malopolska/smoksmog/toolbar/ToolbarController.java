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
import butterknife.OnItemSelected;
import pl.malopolska.smoksmog.BuildConfig;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.StationLocation;
import pl.malopolska.smoksmog.ui.MainActivity;

public class ToolbarController {

    private final Toolbar toolbar;
    private final ActionBar actionBar;
    private final StationAdapter stationAdapter;

    private final List<StationLocation> stationList = new ArrayList<>();
    private final BaseActivity activity;

    @InjectView(R.id.spinner)
    Spinner spinner;

    @Inject
    SmokSmogAPI smokSmogAPI;

    public ToolbarController(@NonNull final BaseActivity activity, @NonNull Toolbar toolbar) {

        Dagger_ToolbarComponent.builder()
                .activityComponent(activity.getActivityComponent())
                .build().inject(this);

        ButterKnife.inject(this, toolbar);

        this.toolbar = toolbar;
        this.activity = activity;

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        stationAdapter = new StationAdapter(activity, stationList);

        spinner.setEnabled(false);
        spinner.setAdapter(stationAdapter);

        updateList(activity);
    }

    @OnItemSelected(R.id.spinner)
    void stationSelected(int position) {
        MainActivity.start(activity, stationList.get(position));
    }

    /**
     * Runs list update for spinner
     *
     * @param activity
     */
    private void updateList(final BaseActivity activity) {

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

                        spinner.setEnabled(true);
                    }
                });
            }
        }).start();
    }
}
