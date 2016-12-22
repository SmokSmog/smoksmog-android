package com.antyzero.smoksmog.ui.screen.start.model

import android.content.SharedPreferences
import com.antyzero.smoksmog.permission.PermissionHelper
import com.antyzero.smoksmog.settings.SettingsHelper

class StationIdList(val settingsHelper: SettingsHelper, val permissionHelper: PermissionHelper) : MutableList<Long> by settingsHelper.stationIdList, SharedPreferences.OnSharedPreferenceChangeListener {

    init {

        val settingsPreferences = settingsHelper.preferences
        settingsPreferences.registerOnSharedPreferenceChangeListener(this)
        updateWithPreferences(settingsPreferences)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == settingsHelper.keyStationClosest) {
            updateWithPreferences(sharedPreferences)
        }
    }

    private fun updateWithPreferences(sharedPreferences: SharedPreferences) {
        val isClosestStation = sharedPreferences.getBoolean(settingsHelper.keyStationClosest, false)
        if (isClosestStation && permissionHelper.isGrantedLocationCorsare) {
            if (size > 0 && this[0] !== 0L) {
                add(0, 0L)
            } else if (size <= 0) {
                add(0L)
            }
        } else {
            if (size > 0 && this[0] === 0L) {
                remove(0)
            }
        }
    }
}
