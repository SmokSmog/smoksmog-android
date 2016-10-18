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
    Answers provideAnswers() {
        return Answers.getInstance();
    }

    @Provides
    @Singleton
    CrashlyticsCore provideCrashlyticsCore() {
        return CrashlyticsCore.getInstance();
    }
}
