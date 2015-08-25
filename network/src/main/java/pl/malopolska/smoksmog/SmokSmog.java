package pl.malopolska.smoksmog;

import com.squareup.okhttp.HttpUrl;

import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.client.Client;

public class SmokSmog {

    private final Api api;
    private final String endpoint;

    /**
     *
     *
     * @param builder
     */
    private SmokSmog( SmokSmog.Builder builder ) {

        RestAdapter.Builder builderRest = new RestAdapter.Builder();

        builderRest.setEndpoint( createEndpoint( builder.endpoint, builder.locale ) );

        if ( builder.client != null ) {
            builderRest.setClient( builder.client );
        }

        RestAdapter restAdapter = builderRest.build();

        api = restAdapter.create( Api.class );
        endpoint = builder.endpoint;
    }

    /**
     *
     *
     * @return
     */
    public Api getApi() {
        return api;
    }

    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Creates full server URL
     *
     * @param endpoint
     * @param locale
     * @return
     */
    static final String createEndpoint( String endpoint, Locale locale ) {
        final HttpUrl parse = HttpUrl.parse( endpoint );

        if ( parse == null ) {
            throw new IllegalArgumentException( "Invlid URL format" );
        }

        return parse.newBuilder().addEncodedPathSegment( locale.getLanguage() ).toString();
    }

    /**
     *
     */
    public static final class Builder {

        private final String endpoint;
        private final Locale locale;

        private Client client;

        public Builder( String endpoint, Locale locale ) {
            this.endpoint = endpoint;
            this.locale = locale;
        }

        public Builder setClient( Client client ) {
            this.client = client;
            return this;
        }

        public SmokSmog build() {
            return new SmokSmog( this );
        }
    }
}
