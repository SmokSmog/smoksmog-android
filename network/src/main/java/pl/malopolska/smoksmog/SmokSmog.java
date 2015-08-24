package pl.malopolska.smoksmog;

import com.squareup.okhttp.HttpUrl;

import java.util.Locale;

import retrofit.RestAdapter;

public class SmokSmog {

    private SmokSmog( SmokSmog.Builder builder ) {

        RestAdapter.Builder builderRest = new RestAdapter.Builder();

        builderRest.setEndpoint( createEndpoint( builder.endpoint, builder.locale ) );
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

        public Builder( String endpoint, Locale locale ) {
            this.endpoint = endpoint;
            this.locale = locale;
        }

        public SmokSmog build() {
            return new SmokSmog( this );
        }
    }
}
