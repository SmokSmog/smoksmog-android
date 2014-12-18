package pl.malopolska.smoksmog.network;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.impl.UrlBuilderImpl;

/**
 * Created by iwopolanski on 18.12.14.
 */
@Module
class SmokSmogModule {

    private final Application application;

    SmokSmogModule( Application application, Configuration configuration ) {
        this.application = application;
    }

    @Provides
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    UrlBuilder providesUrlBuilder( Context context ) {
        return new UrlBuilderImpl(null, null);
    }
}
