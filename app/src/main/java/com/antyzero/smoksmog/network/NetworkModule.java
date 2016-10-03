package com.antyzero.smoksmog.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.antyzero.smoksmog.BuildConfig;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmog;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public SmokSmog provideSmokSmog( Context context ) {
        return new SmokSmog();
    }
}
