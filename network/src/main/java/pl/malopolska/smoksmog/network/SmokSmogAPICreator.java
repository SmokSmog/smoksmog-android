package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.ObjectMapper;

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

        // TODO check what is the last character of endpoint URL
        String baseUrl = endpoint + "/" + locale.getLanguage();

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

        return new JacksonConverter( objectMapper );
    }
}
