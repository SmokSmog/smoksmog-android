package pl.malopolska.smoksmog.network;

import java.util.Collection;

import retrofit.http.GET;

/**
 * Interface for client-server communication. All methods should be synchronized.
 */
public interface SmokSmogAPI {

    /**
     * Get available stations.
     *
     * @return collection of stations
     */
    @GET( "/stations" )
    Collection<StationState> stations();

}
