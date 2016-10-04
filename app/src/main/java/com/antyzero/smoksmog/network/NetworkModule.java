package com.antyzero.smoksmog.network;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmog;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public SmokSmog provideSmokSmog(Context context) {
        return new SmokSmog(getLocale(context));
    }

    private Locale getLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
