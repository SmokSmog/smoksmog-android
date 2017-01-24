package com.antyzero.smoksmog.ui.screen

import android.app.Activity

import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.error.SnackBarErrorReporter
import com.antyzero.smoksmog.firebase.FirebaseEvents
import com.google.firebase.analytics.FirebaseAnalytics

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {
    private val firebaseAnalytics: FirebaseAnalytics

    init {
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
    }

    @Provides
    internal fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return firebaseAnalytics
    }

    @Provides
    internal fun provideFirebaseEvents(firebaseAnalytics: FirebaseAnalytics): FirebaseEvents {
        return FirebaseEvents(firebaseAnalytics)
    }

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    internal fun provideErrorReporter(activity: Activity): ErrorReporter {
        return SnackBarErrorReporter(activity)
    }
}
