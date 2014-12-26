package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;

/**
 * API creator
 */
public final class SmokSmogAPICreator {

    private SmokSmogAPICreator() {
        throw new UnsupportedOperationException( "Utility class" );
    }

    /**
     * Valid way for API creation
     *
     * @param endpoint
     * @param locale
     * @return
     */
    public static SmokSmogAPI create( String endpoint, Locale locale ) {

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

        return restAdapter.create( SmokSmogAPI.class );
    }

    /**
     * Prepare Jackson2 Converter for handling JSON responses.
     *
     * @return Converter implementation
     */
    private static Converter jacksonConverter() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JodaModule());

        return new JacksonConverter( objectMapper );
    }
}
