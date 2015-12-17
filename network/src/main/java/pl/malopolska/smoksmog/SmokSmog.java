package pl.malopolska.smoksmog;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;

import org.joda.time.DateTime;

import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;

public class SmokSmog {

    private final Api api;
    private final String endpoint;

    /**
     * @param builder
     */
    public SmokSmog( SmokSmog.Builder builder ) {

        RestAdapter.Builder builderRest = new RestAdapter.Builder();

        builderRest.setEndpoint( createEndpoint( builder.endpoint, builder.locale ) );

        if( builder.debug ){
            builderRest.setLogLevel( RestAdapter.LogLevel.FULL );
        }

        if ( builder.client != null ) {
            builderRest.setClient( builder.client );
        }

        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter( DateTime.class, new DateTimeDeserializer() );
        Converters.registerLocalDate( gsonBuilder );
        builderRest.setConverter( new GsonConverter( gsonBuilder.create() ) );
        RestAdapter restAdapter = builderRest.build();

        api = restAdapter.create( Api.class );
        endpoint = builder.endpoint;
    }

    /**
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
        private boolean debug = false;

        public Builder( String endpoint, Locale locale ) {
            this.endpoint = endpoint;
            this.locale = locale;
        }

        public Builder setClient( Client client ) {
            this.client = client;
            return this;
        }

        public Builder setDebug( boolean debug ){
            this.debug = debug;
            return this;
        }

        public SmokSmog build() {
            return new SmokSmog( this );
        }
    }
}
