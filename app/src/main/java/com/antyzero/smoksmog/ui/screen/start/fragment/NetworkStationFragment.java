package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.trello.rxlifecycle.FragmentEvent;

import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetworkStationFragment extends StationFragment {

    private static final String TAG = NetworkStationFragment.class.getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        SmokSmogApplication.Companion.get(activity).getAppComponent()
                .plus(new ActivityModule(activity))
                .plus(new FragmentModule(this))
                .inject(this);

        loadData();
    }

    @Override
    protected void loadData() {
        smokSmog.getApi().station(getStationId())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .cast(Station.class)
                .subscribe(
                        new Action1<Station>() {
                            @Override
                            public void call(Station station) {
                                updateUI(station);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                try {
                                    showTryAgain(R.string.error_unable_to_load_station_data);
                                } catch (Exception e) {
                                    logger.e(TAG, "Problem with error handling code", e);
                                } finally {
                                    logger.i(TAG, "Unable to load station data (stationID:" + getStationId() + ")", throwable);
                                }
                            }
                        });
    }
}
