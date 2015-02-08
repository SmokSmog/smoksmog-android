package pl.malopolska.smoksmog.toolbar;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.ui.MainActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ToolbarController extends Subscriber<Collection<StationLocation>> {

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

        SmokSmogApplication.get(activity).getNetworkComponent().inject(this);

        ButterKnife.inject(this, toolbar);

        this.toolbar = toolbar;
        this.activity = activity;

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        stationAdapter = new StationAdapter(activity, stationList);

        spinner.setEnabled(false);
        spinner.setAdapter(stationAdapter);

        updateList();
    }

    @OnItemSelected(R.id.spinner)
    void stationSelected(int position) {
        MainActivity.start(activity, stationList.get(position));
    }

    /**
     * Runs list update for spinner
     */
    private void updateList() {

        smokSmogAPI.stations()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        stationAdapter.notifyDataSetChanged();
        spinner.setEnabled(true);
    }

    @Override
    public void onError(Throwable e) {
        // TODO
    }

    @Override
    public void onNext(Collection<StationLocation> stationLocations) {
        stationList.clear();
        stationList.addAll( stationLocations );
    }
}
