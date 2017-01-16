package com.antyzero.smoksmog.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.permission.PermissionHelper
import java.util.*


/**
 * Working on settings helper.
 *
 *
 * This class should be responsible for data manipulation of every user-changeable data
 */
class SettingsHelper(private val context: Context, permissionHelper: PermissionHelper) : SharedPreferences.OnSharedPreferenceChangeListener {

    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val stationIds: MutableList<Long>

    val keyStationClosest: String = context.getString(R.string.pref_key_station_closest)
    private val keyPercent: String = context.getString(R.string.pref_key_percent)

    var percentMode: Percent? = null
        private set

    private var isClosesStationVisible: Boolean
        get() = preferences.getBoolean(context.getString(R.string.pref_key_station_closest), false)
        set(value) = preferences.edit().putBoolean(keyStationClosest, value).apply()

    init {
        PreferenceManager.setDefaultValues(context, R.xml.settings_general, false)
        preferences.registerOnSharedPreferenceChangeListener(this)
        stationIds = getList(preferences, KEY_STATION_ID_LIST)

        if (!permissionHelper.isGrantedLocationCorsare) {
            isClosesStationVisible = false
        }

        updatePercentMode()
    }

    private fun updatePercentMode(sharedPreferences: SharedPreferences = preferences) {
        val defValue = context.getString(R.string.pref_percent_value_default)
        val string = sharedPreferences.getString(keyPercent, defValue)
        percentMode = Percent.find(context, string!!)
    }

    var stationIdList: MutableList<Long>
        get() = stationIds
        set(longList) {
            val longsTemp = ArrayList(longList)
            stationIds.clear()
            if (isClosesStationVisible) {
                stationIds.add(0L)
            }
            stationIds.addAll(longsTemp)
            preferences.edit().putString(KEY_STATION_ID_LIST, TextUtils.join(SPLIT_CHAR, longList)).apply()
        }

    private fun getList(sharedPreferences: SharedPreferences, key: String): MutableList<Long> {
        val result: MutableList<Long> = mutableListOf()
        val string = sharedPreferences.getString(key, EMPTY_STRING)
        if (!TextUtils.isEmpty(string) && !string.equals(EMPTY_STRING, ignoreCase = true)) {
            string.split(SPLIT_CHAR.toRegex()).dropLastWhile(String::isEmpty).toTypedArray().mapTo(result, String::toLong)
        }
        return result
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == keyPercent) {
            updatePercentMode(sharedPreferences)
        }
    }

    companion object {

        private val EMPTY_STRING = "awd5ijsadf"
        private val SPLIT_CHAR = "%!@"
        private val KEY_STATION_ID_LIST = "KEY_STATION_ID_LIST"
    }
}
