package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.Collection;
import java.util.Locale;

import pl.malopolska.smoksmog.network.model.ParticulateDescription;
import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.network.model.StationParticulates;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * API creator
 */
public final class SmokSmogAPIImpl implements SmokSmogAPI {

    private final SmokSmogAPI smogAPI;

    SmokSmogAPIImpl() {
        throw new UnsupportedOperationException( "Utility class" );
    }

    SmokSmogAPIImpl(String endpoint, Locale locale, Client client){

        if ( endpoint == null ) {
            throw new IllegalArgumentException( "Endpoint cannot be null" );
        }

        if ( locale == null ) {
            throw new IllegalArgumentException( "Locale cannot be null" );
        }

        String extraSlash = endpoint.endsWith( "/" ) ? "" : "/";

        String baseUrl = endpoint + extraSlash + locale.getLanguage();

        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(jacksonConverter());

        // Add custom client
        if( client != null ) {
            restAdapterBuilder.setClient(client);
        }

        smogAPI = restAdapterBuilder.build().create( SmokSmogAPI.class );
    }

    @Override
    public Observable<Collection<StationLocation>> stations() {
        return smogAPI.stations();
    }

    @Override
    public Observable<StationParticulates> station(@Path("stationId") long stationId) {
        return smogAPI.station(stationId);
    }

    @Override
    public Observable<StationParticulates> station(@Path("latitude") float latitude, @Path("longitude") float longitude) {
        return smogAPI.station(latitude, longitude);
    }

    @Override
    public Observable<StationHistory> stationHistory(@Path("stationId") long stationId) {
        return smogAPI.stationHistory(stationId);
    }

    @Override
    public Observable<StationHistory> stationHistory(@Path("latitude") float latitude, @Path("longitude") float longitude) {
        return smogAPI.stationHistory(latitude, longitude);
    }

    @Override
    public Observable<ParticulateDescription> particulate(@Path("particulateId") long particulateId) {
        return smogAPI.particulate(particulateId);
    }

    /**
     * Prepare Jackson2 Converter for handling JSON responses.
     *
     * @return Converter implementation
     */
    private Converter jacksonConverter() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule( new JodaModule() );

        return new JacksonConverter( objectMapper );
    }

    public static SmokSmogAPI create( String endpoint ){
        return create(endpoint, Locale.getDefault());
    }

    public static SmokSmogAPI create( String endpoint, Locale locale ) {
        return create( endpoint, locale, null );
    }

    public static SmokSmogAPI create( String endpoint, Locale locale, Client client ) {
        return new SmokSmogAPIImpl( endpoint, locale, client );
    }
}
