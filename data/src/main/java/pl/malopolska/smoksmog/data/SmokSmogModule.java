package pl.malopolska.smoksmog.data;

import android.content.Context;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.SmokSmogAPIImpl;

/**
 *
 */
@Module(
        library = true,
        injects = {
                SmokSmog.class
        }
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

        return SmokSmogAPIImpl.create(builder.endpoint, locale);
    }
}
