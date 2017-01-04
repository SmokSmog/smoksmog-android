package com.antyzero.smoksmog.fabric

import com.crashlytics.android.answers.ContentViewEvent

import pl.malopolska.smoksmog.model.Station

class StationShowEvent(station: Station) : ContentViewEvent() {

    init {
        putContentId(station.id.toString())
        putContentName(station.name)
        putContentType("Station")
    }
}
