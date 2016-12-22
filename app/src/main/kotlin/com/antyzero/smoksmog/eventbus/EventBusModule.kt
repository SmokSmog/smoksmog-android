package com.antyzero.smoksmog.eventbus

import dagger.Module
import dagger.Provides
import rx.functions.Action1
import smoksmog.logger.Logger
import javax.inject.Named
import javax.inject.Singleton

@Module
class EventBusModule {

    @Provides
    @Singleton
    internal fun provideRxBus(): RxBus {
        return RxBus()
    }
}
