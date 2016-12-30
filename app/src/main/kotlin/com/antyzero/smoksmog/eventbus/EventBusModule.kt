package com.antyzero.smoksmog.eventbus

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EventBusModule {

    @Provides
    @Singleton
    internal fun provideRxBus(): RxBus {
        return RxBus()
    }
}
