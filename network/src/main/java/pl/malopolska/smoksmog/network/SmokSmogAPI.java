package pl.malopolska.smoksmog.network;

import java.util.Collection;

/**
 * Interface for client-server communication. All methods should be synchronized.
 */
public interface SmokSmogAPI {

    /**
     * Get available stations.
     *
     * @return collection of stations
     */
    Collection<StationState> requestStations();
}
