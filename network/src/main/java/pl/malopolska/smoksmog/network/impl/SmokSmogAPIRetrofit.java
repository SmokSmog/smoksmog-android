package pl.malopolska.smoksmog.network.impl;

import java.util.Collection;

import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.StationState;
import retrofit.http.GET;

/**
 * Overrides some of SmokSmogAPI methods to provide Retrofit REST client.
 */
public interface SmokSmogAPIRetrofit extends SmokSmogAPI {

    @Override
    @GET( "/" )
    Collection<StationState> requestStations();
}
