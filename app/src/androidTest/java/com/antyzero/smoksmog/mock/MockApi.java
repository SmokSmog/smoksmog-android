package com.antyzero.smoksmog.mock;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
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

    private static final String TAG = "MockApi";
    private final Gson gson;

    public MockApi( Gson gson ) {
        this.gson = gson;
    }

    @Override
    public Observable<List<Station>> stations() {
        Log.i( TAG, "Called #stations()" );
        return getObservableFromFile( "stations.json", ( Class<List<Station>> ) new Object() );
    }

    @Override
    public Observable<Station> station( @Path( "stationId" ) long stationId ) {
        Log.i( TAG, "Called #station(" + stationId + ")" );
        return getObservableFromFile( "station-4.json", Station.class );
    }

    @Override
    public Observable<Station> stationByLocation( @Path( "lat" ) double latitude, @Path( "lon" ) double longitude ) {
        Log.i( TAG, "Called #station(" + latitude + "," + longitude + ")" );
        return getObservableFromFile( "station-4.json", Station.class );
    }

    @Override
    public Observable<Station> stationHistory( @Path( "stationId" ) long stationId ) {
        Log.i( TAG, "Called #stationHistory(" + stationId + ")" );
        return getObservableFromFile( "station-4-history.json", Station.class );
    }

    @Override
    public Observable<Station> stationHistoryByLocation( @Path( "lat" ) double latitude, @Path( "lon" ) double longitude ) {
        Log.i( TAG, "Called #stationHistory(" + latitude + "," + longitude + ")" );
        return getObservableFromFile( "station-4-history.json", Station.class );
    }

    @Override
    public Observable<Description> particulateDescription( @Path( "id" ) long particulateId ) {
        Log.i( TAG, "Called #particulateDescription(" + particulateId + ")" );
        return getObservableFromFile( "particulates-1.json", Description.class );
    }

    private <T> Observable<T> getObservableFromFile( String fileName, Class<T> classOfT ) {
        return Observable.just( getObjectFromFile( fileName, classOfT ) );
    }

    private <T> T getObjectFromFile( String fileName, Class<T> classOfT ) {
        return gson.fromJson( getStringFromFile( fileName ), classOfT );
    }

    private String getStringFromFile( String fileName ) {

        String result;

        ClassLoader classLoader = getClass().getClassLoader();

        try {
            result = IOUtils.toString( classLoader.getResourceAsStream( "/" + fileName ) );
        } catch ( IOException e ) {
            throw new Error( "Unable to read file" );
        }

        return result;
    }
}
