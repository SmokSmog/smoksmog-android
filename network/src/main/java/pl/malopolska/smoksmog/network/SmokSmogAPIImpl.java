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
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;

/**
 * API creator
 */
public final class SmokSmogAPIImpl implements SmokSmogAPI {

    private final SmokSmogAPI smogAPI;

    SmokSmogAPIImpl() {
        throw new UnsupportedOperationException( "Utility class" );
    }

    SmokSmogAPIImpl(String endpoint, Locale locale){

        if ( endpoint == null ) {
            throw new IllegalArgumentException( "Endpoint cannot be null" );
        }

        if ( locale == null ) {
            throw new IllegalArgumentException( "Locale cannot be null" );
        }

        String extraSlash = endpoint.endsWith( "/" ) ? "" : "/";

        String baseUrl = endpoint + extraSlash + locale.getLanguage();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( baseUrl )
                .setConverter( jacksonConverter() )
                .build();

        smogAPI = restAdapter.create( SmokSmogAPI.class );
    }

    @Override
    public Collection<StationLocation> stations() {
        return smogAPI.stations();
    }

    @Override
    public StationParticulates station( long stationId) {
        return smogAPI.station(stationId);
    }

    @Override
    public StationParticulates station( float latitude, float longitude) {
        return smogAPI.station(latitude, longitude);
    }

    @Override
    public StationHistory stationHistory( long stationId) {
        return smogAPI.stationHistory(stationId);
    }

    @Override
    public StationHistory stationHistory( float latitude, float longitude) {
        return smogAPI.stationHistory(latitude, longitude);
    }

    @Override
    public ParticulateDescription particulate( long particulateId) {
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

    /**
     * Same as create() calling Locale.getDefault()
     *
     * @param endpoint
     * @return
     */
    public static SmokSmogAPI create( String endpoint ){
        return create(endpoint, Locale.getDefault());
    }

    /**
     * Valid way for API creation
     *
     * @param endpoint
     * @param locale
     * @return
     */
    public static SmokSmogAPI create( String endpoint, Locale locale ) {
        return new SmokSmogAPIImpl( endpoint, locale );
    }
}
