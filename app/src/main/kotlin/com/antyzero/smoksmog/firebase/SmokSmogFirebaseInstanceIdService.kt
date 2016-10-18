package com.antyzero.smoksmog.firebase

import android.util.Log
import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.appComponent
import com.antyzero.smoksmog.tag
import com.crashlytics.android.core.CrashlyticsCore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import javax.inject.Inject

class SmokSmogFirebaseInstanceIdService : FirebaseInstanceIdService() {

    @Inject lateinit var crashlyticsCore: CrashlyticsCore

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val token = FirebaseInstanceId.getInstance().token
        if (BuildConfig.DEBUG) {
            Log.i(tag(), "FCM token: $token")
        }
        crashlyticsCore.setString("FCM_TOKEN", token)
    }
}