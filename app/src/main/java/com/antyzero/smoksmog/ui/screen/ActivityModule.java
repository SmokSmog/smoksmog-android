package com.antyzero.smoksmog.ui.screen;

import android.app.Activity;

import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.error.SnackBarErrorReporter;
import com.antyzero.smoksmog.firebase.FirebaseEvents;
import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;
    private final FirebaseAnalytics firebaseAnalytics;

    public ActivityModule(Activity activity) {
        this.activity = activity;
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
    }

    @Provides
    FirebaseAnalytics provideFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    @Provides
    FirebaseEvents provideFirebaseEvents(FirebaseAnalytics firebaseAnalytics){
        return new FirebaseEvents(firebaseAnalytics);
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    ErrorReporter provideErrorReporter(Activity activity) {
        return new SnackBarErrorReporter(activity);
    }
}
