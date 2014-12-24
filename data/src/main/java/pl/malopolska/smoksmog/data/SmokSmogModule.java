package pl.malopolska.smoksmog.data;

import android.content.Context;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.SmokSmogAPICreator;

/**
 *
 */
@Module(
        library = true
)
class SmokSmogModule {

    private final SmokSmog.Builder builder;

    SmokSmogModule( SmokSmog.Builder builder ) {
        this.builder = builder;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return builder.application;
    }

    @Provides
    @Singleton
    SmokSmogAPI providesSmokSmogAPI() {

        Locale locale = builder.application.getResources().getConfiguration().locale;

        return SmokSmogAPICreator.create( builder.endpoint, locale );
    }
}
