package com.antyzero.smoksmog.fabric


import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class FabricModule {

    @Provides
    @Singleton
    internal fun provideAnswers(): Answers {
        return Answers.getInstance()
    }

    @Provides
    @Singleton
    internal fun provideCrashlyticsCore(): CrashlyticsCore {
        return CrashlyticsCore.getInstance()
    }
}
