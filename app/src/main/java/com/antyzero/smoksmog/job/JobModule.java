package com.antyzero.smoksmog.job;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class JobModule {

    @Provides
    @Singleton
    public FirebaseJobDispatcher provideFirebaseJobDispatcher(Context context) {
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }
}
