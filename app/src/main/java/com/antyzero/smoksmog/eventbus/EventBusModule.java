package com.antyzero.smoksmog.eventbus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;
import smoksmog.logger.Logger;

@Module
public class EventBusModule {

    public static final String EVENT_BUS_ERROR = "EventBusError";

    @Provides
    @Singleton
    public RxBus provideRxBus() {
        return new RxBus();
    }

    @Provides
    @Named(EVENT_BUS_ERROR)
    @Singleton
    public Action1<Throwable> provideEventHAndler(Logger logger){
      return throwable -> logger.w("RxEventBus", "Unable to process event", throwable);
    }
}
