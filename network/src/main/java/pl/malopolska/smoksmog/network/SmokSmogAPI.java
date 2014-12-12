package pl.malopolska.smoksmog.network;

import java.util.Collection;

/**
 * TODO add
 */
public interface SmokSmogAPI {

    /**
     * Access to UrlBuilder for SmokSmogAPI
     *
     * @return UrlBuilder implementation
     */
    UrlBuilder getUrlBuilder();

    /**
     * Get available stations.
     *
     * @return collection of stations
     */
    Collection<StationState> requestStations();
}
