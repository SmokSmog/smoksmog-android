package com.antyzero.smoksmog.tracking

import android.content.Context
import android.content.SharedPreferences
import org.joda.time.DateTime

class Tracking(context: Context) {

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences("TrackerSharedPreferences", Context.MODE_PRIVATE)

        // Tracking
        trackFirstRun()
    }

    private fun trackFirstRun() {
        if (preferences.contains(KEY_FIRST_RUN_TIME).not()) {
            preferences.edit().putLong(KEY_FIRST_RUN_TIME, System.currentTimeMillis()).apply()
        }
    }

    fun getFirstRunDateTime(): DateTime = DateTime(preferences.getLong(KEY_FIRST_RUN_TIME, 0))

    private companion object {

        private val KEY_FIRST_RUN_TIME = "firstRun"
    }
}