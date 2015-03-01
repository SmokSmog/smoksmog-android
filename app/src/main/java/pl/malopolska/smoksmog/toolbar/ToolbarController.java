package pl.malopolska.smoksmog.toolbar;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.ui.MainActivity;

public class ToolbarController {

    private final Toolbar toolbar;
    private final ActionBar actionBar;
    private final StationAdapter stationAdapter;

    private final List<StationLocation> stationList = new ArrayList<>();
    private final BaseActivity activity;

    @InjectView(R.id.spinner)
    Spinner spinner;

    public ToolbarController(@NonNull final BaseActivity activity, @NonNull Toolbar toolbar) {

        ButterKnife.inject(this, toolbar);

        this.toolbar = toolbar;
        this.activity = activity;

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        stationAdapter = new StationAdapter(activity, stationList);

        spinner.setEnabled(false);
        spinner.setAdapter(stationAdapter);
    }

    @OnItemSelected(R.id.spinner)
    void stationSelected(int position) {
        MainActivity.start(activity);
    }

    public void setStations(List<StationLocation> stations) {

        stationList.clear();
        stationList.addAll(stations);

        stationAdapter.notifyDataSetChanged();

        spinner.setEnabled(true);
    }

    public void setSelectedStation(@NonNull StationLocation closestStation) {

        for( int i = 0; i < stationAdapter.getCount(); i++ ){

            if( stationAdapter.getItem(i).getId() == closestStation.getId()){
                spinner.setSelection(i, true);
                break;
            }
        }
    }
}
