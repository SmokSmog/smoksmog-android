package com.antyzero.smoksmog.wear;


import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmog;

@Module
@Singleton
public class NetworkModule {

    private static final String ENDPOINT = "http://api.smoksmog.jkostrz.name/";

    @Provides
    @Singleton
    public SmokSmog provideSmokSmog( Context context ) {
        final SmokSmog.Builder builder = createBuilder( context );
        builder.setDebug( BuildConfig.DEBUG );
        return builder.build();
    }

    @NonNull
    protected SmokSmog.Builder createBuilder( Context context ) {
        final Locale locale = context.getResources().getConfiguration().locale;
        return new SmokSmog.Builder( ENDPOINT, locale );
    }

}
