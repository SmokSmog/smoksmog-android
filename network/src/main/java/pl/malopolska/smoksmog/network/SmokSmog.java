package pl.malopolska.smoksmog.network;

import android.content.Context;

import java.util.Locale;

import pl.malopolska.smoksmog.network.impl.SmokSmogAPIRetrofit;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;

/**
 *
 */
public final class SmokSmog {

    /**
     * Get API implementation.
     *
     * @param context required for components
     * @return SmokSmogAPI object
     */
    public static SmokSmogAPI getInstance( Context context, String baseUrl ,Locale locale ){

        // TODO check '/'
        String url = baseUrl + locale.getLanguage();

        Endpoint endpoint = Endpoints.newFixedEndpoint(url);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( endpoint )
                .build();

        return restAdapter.create( SmokSmogAPIRetrofit.class );
    }

    /**
     * Get API implementation.
     *
     * @param context required for components
     * @return SmokSmogAPI object
     */
    public static SmokSmogAPI getInstance( Context context, String baseUrl ){
        return getInstance( context, baseUrl, context.getResources().getConfiguration().locale );
    }
}
