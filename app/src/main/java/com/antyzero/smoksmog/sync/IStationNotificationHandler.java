package com.antyzero.smoksmog.sync;

import pl.malopolska.smoksmog.model.Station;

interface IStationNotificationHandler {
    void handleNotification(Station station);
}
