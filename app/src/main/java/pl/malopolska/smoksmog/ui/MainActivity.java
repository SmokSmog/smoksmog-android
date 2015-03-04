package pl.malopolska.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.data.StationUtils;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.model.Station;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.toolbar.ToolbarController;
import pl.malopolska.smoksmog.ui.preference.PreferenceActivity;
import pl.malopolska.smoksmog.ui.view.PollutionIndicatorView;
import rx.Observable;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Observer<Collection<StationLocation>> {

    private final List<StationLocation> stations = new ArrayList<>();

    private ToolbarController toolbarController;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.pollutionIndicator)
    PollutionIndicatorView pollutionIndicator;

    @Inject
    SmokSmogAPI smokSmogAPI;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmokSmogApplication smokSmogApplication = SmokSmogApplication.get(this);

        smokSmogApplication.getApplicationComponent().inject(this);
        smokSmogApplication.getNetworkComponent().inject(this);

        ButterKnife.inject(this);

        toolbarController = new ToolbarController(this, toolbar);

        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);

        Observable<Float> floatObservable = Observable.just(new Random().nextFloat() * 6);

        AppObservable.bindActivity(this, floatObservable)
                .delay(5, TimeUnit.SECONDS)
                .repeat(1000)
                .doOnNext( value -> runOnUiThread(() -> pollutionIndicator.setValue(new Random().nextFloat() * 6)))
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO disable for this stage
        // getMenuInflater().inflate(R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean result = super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            PreferenceActivity.start(this);
            result = true;
        }

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();

        AppObservable.bindActivity(this, smokSmogAPI.stations())
                .subscribeOn(Schedulers.io())
                .subscribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onNext(Collection<StationLocation> stationLocations) {
        this.stations.clear();
        this.stations.addAll(stationLocations);
    }

    @Override
    public void onCompleted() {
        toolbarController.setStations(stations);

        Location lastLocation = LocationServices.
                FusedLocationApi.getLastLocation(googleApiClient);

        StationLocation closestStation = StationUtils.findClosestStation(stations, lastLocation);

        toolbarController.setSelectedStation(closestStation);
    }

    @Override
    public void onError(Throwable e) {

    }

    /**
     * Google Api Client connected
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        // do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO unable to get location service
    }

    /**
     * Starts activity with default sation selected
     *
     * @param context for starting Activity
     */
    public static void start(Context context) {

        Intent intent = new Intent(context, MainActivity.class);

        context.startActivity(intent);
    }
}
