package com.antyzero.smoksmog.wear;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmog;

@Module
@Singleton
public class NetworkModule {

    @Provides
    @Singleton
    public SmokSmog provideSmokSmog() {
        return new SmokSmog();
    }
}
