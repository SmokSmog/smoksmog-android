package com.antyzero.smoksmog.sync;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;

import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;

class StationNotificationHandler implements IStationNotificationHandler {

    private Context context;
    
    public StationNotificationHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void handleNotification(Station station) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setContentTitle(station.getName());

        String contentText = getNotificationContentText(station, context.getString(R.string.notificationLevelExceededFor));
        notificationBuilder.setContentText(contentText);

        Intent intent = new Intent(context, StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.smoksmog);

        Notification notification = notificationBuilder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notification);
    }

    @VisibleForTesting
    @NonNull
    String getNotificationContentText(Station station, String levelExceededLabel) {
        StringBuilder contentTextBuilder = new StringBuilder();
        for (Particulate particulate : station.getParticulates()) {
            if (isLevelExceeded(particulate)) {
                if (contentTextBuilder.length() != 0) {
                    contentTextBuilder.append(", ");
                    contentTextBuilder.append(particulate.getShortName());
                } else {
                    contentTextBuilder.append(String.format(levelExceededLabel, particulate.getShortName()));
                }
            }
        }

        return contentTextBuilder.toString();
    }

    @VisibleForTesting
    boolean isLevelExceeded(Particulate particulate) {
        return true;//particulate.getValue() > particulate.getNorm();
    }
}
