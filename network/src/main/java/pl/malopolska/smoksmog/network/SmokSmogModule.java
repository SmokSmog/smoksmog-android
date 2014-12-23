package pl.malopolska.smoksmog.network;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.impl.SmokSmogAPIRetrofit;
import pl.malopolska.smoksmog.network.impl.UrlBuilderImpl;
import retrofit.RestAdapter;

/**
 *
 */
@Module(
        library = true
)
class SmokSmogModule {

    private final Application application;

    SmokSmogModule( Application application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    UrlBuilder providesUrlBuilder( Context context ) {
        return new UrlBuilderImpl( null, null );
    }

    @Provides
    @Singleton
    SmokSmogAPI providesSmokSmogAPI( UrlBuilder urlBuilder ) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( urlBuilder.baseUrl() )
                .build();

        return restAdapter.create( SmokSmogAPIRetrofit.class );
    }
}
