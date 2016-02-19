package com.antyzero.smoksmog.eventbus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {

    @Provides
    @Singleton
    public RxBus provideRxBus(){
        return new RxBus();
    }
}
