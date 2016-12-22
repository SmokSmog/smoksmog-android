package com.antyzero.smoksmog.ui.screen.order;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.BaseDragonActivity;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.PickStationActivity;
import com.antyzero.smoksmog.ui.screen.order.dialog.StationDialogAdapter;
import com.antyzero.smoksmog.ui.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import pl.malopolska.smoksmog.utils.StationUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import smoksmog.logger.Logger;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class OrderActivity extends BaseDragonActivity implements OnStartDragListener, StationDialogAdapter.StationListener {

    private static final String TAG = OrderActivity.class.getSimpleName();
    private static final String EXTRA_DIALOG = "EXTRA_DIALOG";
    private static final int PICK_STATION_REQUEST = 8925;
    private final List<Station> stationList = new ArrayList<>();
    @Inject
    SmokSmog smokSmog;
    @Inject
    SettingsHelper settingsHelper;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private ItemTouchHelper itemTouchHelper;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean stationPickUp) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(EXTRA_DIALOG, stationPickUp);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setSupportActionBar(toolbar);
        getContainer().setPadding(0, DimenUtils.getStatusBarHeight(this), 0, 0);

        if (getIntent() != null && getIntent().getBooleanExtra(EXTRA_DIALOG, false)) {
            startStationPick();
        }

        setupFAB();
        setupNavigationBar();

        recyclerView.setPadding(
                0, 0,
                0, getResources().getDimensionPixelSize(R.dimen.item_air_quality_height) * 3);

        SmokSmogApplication.Companion.get(this)
                .getAppComponent()
                .plus(new ActivityModule(this))
                .inject(this);

        OrderAdapter adapter = new OrderAdapter(stationList, this, settingsHelper);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        final List<Long> stationIds = settingsHelper.getStationIdList();

        smokSmog.getApi().stations()
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<List<Station>, Observable<Station>>() {
                    @Override
                    public Observable<Station> call(List<Station> stations) {
                        return Observable.from(stations);
                    }
                })
                .filter(new Func1<Station, Boolean>() {
                    @Override
                    public Boolean call(Station station) {
                        return stationIds.contains(station.getId());
                    }
                })
                .toSortedList(new Func2<Station, Station, Integer>() {
                    @Override
                    public Integer call(Station station, Station station2) {
                        return stationIds.indexOf(station.getId()) - stationIds.indexOf(station2.getId());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<Station>>() {
                            @Override
                            public void call(List<Station> stations) {
                                stationList.clear();
                                stationList.addAll(stations);
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                logger.w(TAG, "Unable to build list", throwable);
                            }
                        });

    }

    private void setupFAB() {
        int margin = getResources().getDimensionPixelSize(R.dimen.margin_16);

        LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);

        int bottomMargin = margin;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomMargin += DimenUtils.getNavBarHeight(this);
        }

        params.bottomMargin = bottomMargin;
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.anchorGravity = Gravity.BOTTOM | Gravity.END;
        params.setAnchorId(R.id.recyclerView);

        floatingActionButton.setLayoutParams(params);
    }

    private void setupNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        startStationPick();
    }

    private void startStationPick() {
        List<Long> list = Observable.from(stationList)
                .map(new Func1<Station, Long>() {
                    @Override
                    public Long call(Station station) {
                        return station.getId();
                    }
                })
                .toList()
                .toBlocking().first();
        int[] ids = convertListToArray(list);
        PickStationActivity.Companion.startForResult(this, PICK_STATION_REQUEST, ids);
    }

    private int[] convertListToArray(List<Long> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_STATION_REQUEST) {
            if (resultCode == RESULT_OK) {
                onStation(PickStationActivity.gatherResult(data).getFirst());
            } else {
                Toast.makeText(this, "Nie wybrano stacji", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onStation(long stationId) {
        smokSmog.getApi().station(stationId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Station>() {
                            @Override
                            public void call(Station station) {
                                stationList.add(station);
                                recyclerView.getAdapter().notifyDataSetChanged();
                                settingsHelper.setStationIdList(StationUtils.Companion.convertStationsToIdsList(stationList));
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                logger.e(TAG, "Unable to add station to station list", throwable);
                                errorReporter.report(R.string.error_unable_to_add_station);
                            }
                        }
                );
    }
}
