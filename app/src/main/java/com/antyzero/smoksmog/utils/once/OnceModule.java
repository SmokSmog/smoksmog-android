package com.antyzero.smoksmog.utils.once;

import android.content.Context;

import com.antyzero.smoksmog.eventbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jonathanfinerty.once.Once;

@Module
@Singleton
public class OnceModule {

    public OnceModule(Context context) {
        Once.initialise(context);
    }

    @Provides
    @Singleton
    public OnceFacebookInfo provideFacebookDialog(RxBus rxBus){
        return new OnceFacebookInfo(rxBus);
    }
}
