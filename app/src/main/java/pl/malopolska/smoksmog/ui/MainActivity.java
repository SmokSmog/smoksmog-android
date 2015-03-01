package pl.malopolska.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.model.Station;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.toolbar.ToolbarController;
import pl.malopolska.smoksmog.ui.preference.PreferenceActivity;
import rx.Observer;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Observer<Collection<StationLocation>> {

    private static final String EXTRA_STATION_ID = "EXTRA_STATION_ID";
    private static final long NO_STATION_SELECTED = Long.MIN_VALUE;

    private final List<StationLocation> stations = new ArrayList<>();

    private ToolbarController toolbarController;

    private long stationIdSelected = NO_STATION_SELECTED;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SmokSmogAPI smokSmogAPI;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmokSmogApplication.get(this).getApplicationComponent().inject(this);
        SmokSmogApplication.get(this).getNetworkComponent().inject(this);

        ButterKnife.inject(this);

        processIntent(getIntent());

        toolbarController = new ToolbarController(this, toolbar);

        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
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

    /**
     * Checks intent for extras
     *
     * @param intent given
     */
    private void processIntent(Intent intent) {
        stationIdSelected = intent.getLongExtra(EXTRA_STATION_ID, NO_STATION_SELECTED);
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
    public void onCompleted() {

        Location lastLocation = LocationServices.
                FusedLocationApi.getLastLocation(googleApiClient);

        Toast.makeText(this, "" + lastLocation, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Collection<StationLocation> stationLocations) {
        this.stations.clear();
        this.stations.addAll(stationLocations);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        // TODO get nearest station for that location
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
     * @param station provides access to ID
     */
    public static void start(Context context, Station station) {

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(EXTRA_STATION_ID, station.getId());

        context.startActivity(intent);
    }
}
