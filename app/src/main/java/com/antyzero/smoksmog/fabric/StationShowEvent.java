package com.antyzero.smoksmog.fabric;

import com.crashlytics.android.answers.ContentViewEvent;

import pl.malopolska.smoksmog.model.Station;

public class StationShowEvent extends ContentViewEvent {

    public StationShowEvent(Station station) {
        putContentId(String.valueOf(station.getId()));
        putContentName(station.getName());
        putContentType("Station");
    }
}
