package com.antyzero.smoksmog.fabric;

import com.crashlytics.android.answers.ContentViewEvent;

import pl.malopolska.smoksmog.model.Station;

public class StationShowEvent extends ContentViewEvent {

    public static StationShowEvent create( Station station ){

        StationShowEvent event = new StationShowEvent();

        event.putContentId( String.valueOf( station.getId() ) );
        event.putContentName( station.getName() );
        event.putContentType( "Station" );

        return event;
    }
}
