package com.antyzero.smoksmog.sync;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.ui.ServiceModule;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.SmokSmog;
import smoksmog.logger.Logger;

/**
 * An implementation of {@link com.google.android.gms.gcm.GcmTaskService} (backed up by regular {@link android.app.Service class})
 * for fetching data in the background and handling system notifications.
 */
public class SyncService extends GcmTaskService {

    public static final String TAG = "smok-smog-sync-service";

    @Inject
    SmokSmog smokSmog;

    @Inject
    ErrorReporter errorReporter;

    @Inject
    Logger logger;

    @Inject
    IStationNotificationHandler stationNotificationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        SmokSmogApplication.get(this).getAppComponent().plus(new ServiceModule(this)).inject(this);

    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        loadDataForCurrentLocation();
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    /**
     * Check current location and load data
     */
    private void loadDataForCurrentLocation() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider(getApplicationContext());

        reactiveLocationProvider.getLastKnownLocation()
                .concatMap(location -> smokSmog.getApi().stationByLocation(location.getLatitude(), location.getLongitude()))
                .subscribe(
                        station -> stationNotificationHandler.handleNotification(station),
                        throwable -> {
                            logger.i(TAG, "Unable to find nearest station data", throwable);
                            errorReporter.report(R.string.error_no_near_Station);
                        }
                );
    }
}
