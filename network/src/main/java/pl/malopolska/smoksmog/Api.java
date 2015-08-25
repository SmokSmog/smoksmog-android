package pl.malopolska.smoksmog;

import java.util.List;

import pl.malopolska.smoksmog.model.Description;
import pl.malopolska.smoksmog.model.Station;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface Api {

    @GET( "/stations" )
    Observable<List<Station>> stations();

    @GET( "/stations/{stationId}" )
    Observable<Station> station( @Path( "stationId" ) long stationId );

    @GET( "/stations/{lat}/{lon}" )
    Observable<Station> stationByLocation( @Path( "lat" ) float latitude, @Path( "lon" ) float longitude );

    @GET( "/stations/{stationId}/history" )
    Observable<Station> stationHistory( @Path( "stationId" ) long stationId );

    @GET( "/stations/{lat}/{lon}/history" )
    Observable<Station> stationHistoryByLocation( @Path( "lat" ) float latitude, @Path( "lon" ) float longitude );

    @GET( "/particulates/{id}/desc" )
    Observable<Description> particulateDescription( @Path( "id" ) long particulateId );
}
