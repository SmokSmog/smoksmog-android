package pl.malopolska.smoksmog.network.impl;

import java.util.Collection;

import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.StationState;

/**
 * Overrides some of SmokSmogAPI methods to provide capability with Retrofit REST client generator/
 */
public interface SmokSmogAPIRetrofit extends SmokSmogAPI {

    @Override
    Collection<StationState> requestStations();
}
