package com.antyzero.smoksmog.firebase

import android.util.Log
import com.antyzero.smoksmog.tag
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class SmokSmogFirebaseInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        Log.i(tag(), "FCM token: ${FirebaseInstanceId.getInstance().token}")
    }
}