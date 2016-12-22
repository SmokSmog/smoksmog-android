package com.antyzero.smoksmog.ui.screen.start.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LocationStationFragment extends StationFragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = LocationStationFragment.class.getSimpleName();

    private static final long LOCATION_TIMEOUT_IN_SECONDS = 4;
    @Inject
    GoogleApiClient googleApiClient;
    private Location locationCurrent;
    private Location locationStation;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        SmokSmogApplication.Companion.get(activity).getAppComponent()
                .plus(new ActivityModule(activity))
                .plus(new FragmentModule(this), new GoogleModule(this))
                .inject(this);

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        loadData();
    }

    @Override
    protected void loadData() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider(getActivity());

        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setNumUpdates(1)
                .setExpirationDuration(TimeUnit.SECONDS.toMillis(LOCATION_TIMEOUT_IN_SECONDS));

        reactiveLocationProvider.getUpdatedLocation(request)
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .first()
                .cast(Location.class)
                .doOnNext(new Action1<Location>() {
                    @Override
                    public void call(Location location) {
                        LocationStationFragment.this.locationCurrent = location;
                    }
                })
                .flatMap(new Func1<Location, Observable<Station>>() {
                    @Override
                    public Observable<Station> call(Location location) {
                        return smokSmog.getApi().stationByLocation(location.getLatitude(), location.getLongitude());
                    }
                })
                .doOnNext(new Action1<Station>() {
                    @Override
                    public void call(final Station givenStation) {

                        smokSmog.getApi().stations()
                                .concatMap(new Func1<List<Station>, Observable<Station>>() {
                                    @Override
                                    public Observable<Station> call(List<Station> stations) {
                                        return Observable.from(stations);
                                    }
                                })
                                .filter(new Func1<Station, Boolean>() {
                                    @Override
                                    public Boolean call(Station station) {
                                        return station.getId() == givenStation.getId();
                                    }
                                })
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                                .cast(Station.class)
                                .subscribe(new Action1<Station>() {
                                    @Override
                                    public void call(Station station1) {
                                        locationStation = new Location(locationCurrent);
                                        locationStation.setLongitude(station1.getLongitude());
                                        locationStation.setLatitude(station1.getLatitude());
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                                   @Override
                                   public void call() {
                                       LocationStationFragment.this.locationCurrent = null;
                                       showLoading();
                                   }
                               }
                )
                .cast(Station.class)
                .subscribe(
                        new Action1<Station>() {
                            @Override
                            public void call(final Station station) {
                                updateViewsOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateUI(station);
                                    }
                                });
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                try {
                                    showTryAgain(R.string.error_no_near_Station);
                                } catch (Exception e) {
                                    logger.e(TAG, "Problem with error handling code", e);
                                } finally {
                                    logger.i(TAG, "Unable to find closes station", throwable);
                                }
                            }
                        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        // do nothing
    }
}
