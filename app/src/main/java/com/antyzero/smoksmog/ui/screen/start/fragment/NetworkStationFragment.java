package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.trello.rxlifecycle.FragmentEvent;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkStationFragment extends StationFragment {

    private static final String TAG = NetworkStationFragment.class.getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        SmokSmogApplication.get(activity).getAppComponent()
                .plus(new ActivityModule(activity))
                .plus(new FragmentModule(this))
                .inject(this);

        loadData();
    }

    @Override
    protected void loadData() {
        smokSmog.getApi().station(getStationId())
                .doOnSubscribe(this::showLoading)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(
                        this::updateUI,
                        throwable -> {
                            try {
                                showTryAgain(R.string.error_unable_to_load_station_data);
                            } catch (Exception e) {
                                logger.e(TAG, "Problem with error handling code", e);
                            } finally {
                                logger.i(TAG, "Unable to load station data (stationID:" + getStationId() + ")", throwable);
                            }
                        });
    }
}
