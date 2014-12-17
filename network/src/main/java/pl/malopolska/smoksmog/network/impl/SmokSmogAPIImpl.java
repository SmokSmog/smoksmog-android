package pl.malopolska.smoksmog.network.impl;

import android.content.Context;

import pl.malopolska.smoksmog.network.SmokSmogAPI;
import retrofit.RestAdapter;

/**
 *
 */
public final class SmokSmogAPIImpl {

    /**
     * Get API implementation.
     *
     * @param context required for components
     * @return SmokSmogAPI object
     */
    public static SmokSmogAPI getInstance( Context context ){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .build();

        return restAdapter.create( SmokSmogAPIRetrofit.class );
    }
}
