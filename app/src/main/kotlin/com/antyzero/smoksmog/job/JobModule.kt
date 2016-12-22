package com.antyzero.smoksmog.job

import android.content.Context

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
@Singleton
class JobModule {

    @Provides
    @Singleton
    internal fun provideFirebaseJobDispatcher(context: Context): FirebaseJobDispatcher {
        return FirebaseJobDispatcher(GooglePlayDriver(context))
    }
}
