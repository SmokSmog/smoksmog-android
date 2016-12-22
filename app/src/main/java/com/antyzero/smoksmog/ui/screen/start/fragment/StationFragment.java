package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.fabric.StationShowEvent;
import com.antyzero.smoksmog.ui.BaseFragment;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;
import com.antyzero.smoksmog.ui.screen.start.StationAdapter;
import com.antyzero.smoksmog.ui.screen.start.TitleProvider;
import com.crashlytics.android.answers.Answers;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import smoksmog.logger.Logger;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public abstract class StationFragment extends BaseFragment implements TitleProvider {

    private static final String TAG = StationFragment.class.getSimpleName();
    private static final String ARG_STATION_ID = "argStationId";
    private static final int NEAREST_STATION_ID = 0;

    //<editor-fold desc="Views">
    @Bind(R.id.viewAnimator)
    ViewAnimator viewAnimator;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.textViewError)
    TextView textViewError;
    //</editor-fold>

    //<editor-fold desc="Injects">
    @Inject
    RxBus rxBus;
    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    Answers answers;
    //</editor-fold>

    private List<Station> stationContainer = new ArrayList<>();
    private Station station;

    /**
     * Proper way to create fragment
     *
     * @param stationId for data download
     * @return StationFragment instance
     */
    public static StationFragment newInstance(long stationId) {

        Bundle arguments = new Bundle();

        arguments.putLong(ARG_STATION_ID, stationId);

        StationFragment stationFragment = stationId <= 0 ?
                new LocationStationFragment() :
                new NetworkStationFragment();
        stationFragment.setArguments(arguments);

        return stationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_STATION_ID)) {
            throw new IllegalStateException(String.format(
                    "%s should be created with newInstance method, missing ARG_STATION_ID",
                    StationFragment.class.getSimpleName()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_station, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        recyclerView.setAdapter(new StationAdapter(stationContainer));

        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.accent),
                PorterDuff.Mode.SRC_IN);

        showLoading();
    }

    /**
     * Implement for data load
     */
    protected abstract void loadData();

    @Override
    public String getTitle() {
        return station == null ? null : station.getName();
    }

    @Override
    public String getSubtitle() {
        return getStationId() == NEAREST_STATION_ID ? getString(R.string.station_closest) : null;
    }

    protected final void showLoading() {
        updateViewsOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewAnimator.setDisplayedChild(1);
            }
        });
    }

    protected final void showData() {
        updateViewsOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewAnimator.setDisplayedChild(0);
            }
        });
    }

    protected final void showTryAgain(@StringRes final int errorReport) {
        updateViewsOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewAnimator.setDisplayedChild(2);
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText(getString(errorReport));
            }
        });
    }

    /**
     * Use to update views on main thread, in case fragment is not accessible (post view destroy)
     * ignore this call.
     *
     * @param givenRunnable for view update
     */
    protected void updateViewsOnUiThread(Runnable givenRunnable) {
        Observable.just(givenRunnable)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .cast(Runnable.class)
                .filter(new Func1<Runnable, Boolean>() {
                    @Override
                    public Boolean call(Runnable runnable) {
                        return !isDetached();
                    }
                })
                .subscribe(new Action1<Runnable>() {
                               @Override
                               public void call(Runnable runnable) {
                                   runnable.run();
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                logger.e(TAG, "Unable to update views");
                            }
                        });
    }

    @OnClick(R.id.buttonTryAgain)
    void buttonReloadData() {
        textViewError.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewError.setVisibility(View.GONE);
            }
        }, 1000L);
        loadData();
    }

    /**
     * Access fragment station
     *
     * @return Station instance or null if station is not yet loaded
     */
    @Nullable
    public Station getStation() {
        return station;
    }

    /**
     * Update UI with new station data
     *
     * @param station data
     */
    protected void updateUI(Station station) {

        this.station = station;

        stationContainer.clear();
        stationContainer.add(station);
        recyclerView.getAdapter().notifyDataSetChanged();

        rxBus.send(new StartActivity.TitleUpdateEvent());

        answers.logContentView(new StationShowEvent(station));

        showData();
    }

    /**
     * Get station Id used to create this fragment
     *
     * @return station ID
     */
    public long getStationId() {
        return getArguments().getLong(ARG_STATION_ID);
    }
}
