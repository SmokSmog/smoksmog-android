package pl.malopolska.smoksmog.network;

import java.util.Collection;

import pl.malopolska.smoksmog.network.model.ParticulateDescription;
import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.network.model.StationParticulates;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interface for client-server communication. All methods should be synchronized.
 */
public interface SmokSmogAPI {

    /**
     * Get available stations.
     *
     * @return collection of stations
     */
    @GET("/stations")
    Collection<StationLocation> stations();

    @GET( "/stations/{stationId}" )
    StationParticulates station( @Path( "stationId" ) long stationId );

    @GET( "/stations/{latitude}/{longitude}" )
    StationParticulates station( @Path( "latitude" ) float latitude, @Path( "longitude" ) float longitude );

    @GET( "/stations/{stationId}/history" )
    StationHistory stationHistory( @Path( "stationId" ) long stationId );

    @GET( "/stations/{latitude}/{longitude}/history" )
    StationHistory stationHistory( @Path( "latitude" ) float latitude, @Path( "longitude" ) float longitude );

    @GET( "/particulates/{particulateId}/desc" )
    ParticulateDescription particulate( @Path( "particulateId" ) long particulateId );
}
