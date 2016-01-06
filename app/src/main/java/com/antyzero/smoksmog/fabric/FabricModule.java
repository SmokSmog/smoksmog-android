package com.antyzero.smoksmog.fabric;


import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class FabricModule {

    @Provides
    @Singleton
    public Answers provideAnswers() {
        return Answers.getInstance();
    }

    @Provides
    @Singleton
    public CrashlyticsCore provideCrashlyticsCore() {
        return CrashlyticsCore.getInstance();
    }
}
