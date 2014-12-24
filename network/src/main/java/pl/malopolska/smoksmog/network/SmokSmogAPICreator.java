package pl.malopolska.smoksmog.network;

import java.util.Locale;

import retrofit.RestAdapter;

/**
 * Created by iwopolanski on 24.12.14.
 */
public final class SmokSmogAPICreator {

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

        String baseUrl = endpoint + "/" + locale.getLanguage();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( baseUrl )
                .build();

        return restAdapter.create( SmokSmogAPI.class );
    }
}
