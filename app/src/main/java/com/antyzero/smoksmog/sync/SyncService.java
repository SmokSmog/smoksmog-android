package com.antyzero.smoksmog.sync;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.antyzero.smoksmog.ApplicationModule;
import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.ServiceModule;
import com.antyzero.smoksmog.ui.screen.main.MainActivity;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;

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

    @Override
    public void onCreate() {
        super.onCreate();
        SmokSmogApplication.get(this).getAppComponent().plus(new ServiceModule(this)).inject(this);

    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        loadDataForCurrentLocation();
        return 0;
    }

    /**
     * Check current location and load data
     */
    private void loadDataForCurrentLocation() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider(getApplicationContext());

        reactiveLocationProvider.getLastKnownLocation()
                .concatMap(location -> smokSmog.getApi().stationByLocation(location.getLatitude(), location.getLongitude()))
                .subscribe(
                        this::handleSystemNotification,
                        throwable -> {
                            logger.i(TAG, "Unable to find nearest station data", throwable);
                            errorReporter.report(R.string.error_no_near_Station);
                        }
                );
    }

    private void handleSystemNotification(Station station) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
        notificationBuilder.setContentTitle(station.getName());

        StringBuilder contentTextBuilder = new StringBuilder();
        for (Particulate particulate : station.getParticulates()) {
            if (particulate.getValue() > particulate.getNorm()) {
                if (contentTextBuilder.length() != 0) {
                    contentTextBuilder.append(", ");
                    contentTextBuilder.append(particulate.getShortName());
                } else {
                    contentTextBuilder.append("Przekroczony poziom dla: ");
                    contentTextBuilder.append(particulate.getShortName());
                }
            }
        }

        notificationBuilder.setContentText(contentTextBuilder);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0, notification);
    }
}
