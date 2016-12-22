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

    val preferences: SharedPreferences

    private val stationIds: MutableList<Long>

    val keyStationClosest: String
    private val keyPercent: String

    var percentMode: Percent? = null
        private set

    private var isClosesStationVisible: Boolean
        get() = preferences.getBoolean(context.getString(R.string.pref_key_station_closest), false)
        set(value) = preferences.edit().putBoolean(keyStationClosest, value).apply()

    init {

        PreferenceManager.setDefaultValues(context, R.xml.settings_general, false)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.registerOnSharedPreferenceChangeListener(this)
        stationIds = getList(preferences, KEY_STATION_ID_LIST, Long::class.java)

        keyStationClosest = context.getString(R.string.pref_key_station_closest)
        keyPercent = context.getString(R.string.pref_key_percent)

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

    var stationIdList: List<Long>
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

    private fun <T> getList(sharedPreferences: SharedPreferences, key: String, type: Class<T>): MutableList<T> {
        val result = ArrayList<T>()
        val string = sharedPreferences.getString(key, EMPTY_STRING)
        if (!TextUtils.isEmpty(string) && !string!!.equals(EMPTY_STRING, ignoreCase = true)) {

            val array = string.split(SPLIT_CHAR.toRegex()).dropLastWhile(String::isEmpty).toTypedArray()

            for (item in array) {
                TODO("Converter work in progress, we should cat item type to type ?")
                // result.add(stringConvert.getConverterFor(type).convert(item) as T)
            }
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
