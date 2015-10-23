package com.antyzero.smoksmog.network;

import android.content.Context;

import com.antyzero.smoksmog.BuildConfig;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmog;

@Module
public class NetworkModule {

    private static final String ENDPOINT = "http://api.smoksmog.jkostrz.name/";

    @Provides
    @Singleton
    public SmokSmog provideSmokSmog( Context context ) {

        final Locale locale = context.getResources().getConfiguration().locale;
        final SmokSmog.Builder builder = new SmokSmog.Builder( ENDPOINT, locale );

        builder.setDebug( BuildConfig.DEBUG );

        return builder.build();
    }
}
