package pl.malopolska.smoksmog.network;

import java.util.Collection;

import pl.malopolska.smoksmog.network.model.ParticulateDescription;
import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.network.model.StationParticulates;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

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
    Observable<Collection<StationLocation>> stations();

    @GET( "/stations/{stationId}" )
    Observable<StationParticulates> station( @Path( "stationId" ) long stationId );

    @GET( "/stations/{latitude}/{longitude}" )
    Observable<StationParticulates> station( @Path( "latitude" ) float latitude, @Path( "longitude" ) float longitude );

    @GET( "/stations/{stationId}/history" )
    Observable<StationHistory> stationHistory( @Path( "stationId" ) long stationId );

    @GET( "/stations/{latitude}/{longitude}/history" )
    Observable<StationHistory> stationHistory( @Path( "latitude" ) float latitude, @Path( "longitude" ) float longitude );

    @GET( "/particulates/{particulateId}/desc" )
    Observable<ParticulateDescription> particulate( @Path( "particulateId" ) long particulateId );
}
