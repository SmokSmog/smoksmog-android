package com.antyzero.smoksmog.mock;

import java.util.List;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.model.Description;
import pl.malopolska.smoksmog.model.Station;
import retrofit.http.Path;
import rx.Observable;

/**
 * Mock API service
 */
public class MockApi implements Api {

    public MockApi() {

    }

    @Override
    public Observable<List<Station>> stations() {
        return null;
    }

    @Override
    public Observable<Station> station( @Path( "stationId" ) long stationId ) {
        return null;
    }

    @Override
    public Observable<Station> stationByLocation( @Path( "lat" ) double latitude, @Path( "lon" ) double longitude ) {
        return null;
    }

    @Override
    public Observable<Station> stationHistory( @Path( "stationId" ) long stationId ) {
        return null;
    }

    @Override
    public Observable<Station> stationHistoryByLocation( @Path( "lat" ) double latitude, @Path( "lon" ) double longitude ) {
        return null;
    }

    @Override
    public Observable<Description> particulateDescription( @Path( "id" ) long particulateId ) {
        return null;
    }
}
