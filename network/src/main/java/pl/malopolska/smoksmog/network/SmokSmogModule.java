package pl.malopolska.smoksmog.network;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.impl.UrlBuilderImpl;

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
        return new UrlBuilderImpl(null, null);
    }
}
