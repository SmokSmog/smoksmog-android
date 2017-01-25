package com.antyzero.smoksmog.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.antyzero.smoksmog.R
import java.util.*


/**
 * Working on settings helper.
 *
 *
 * This class should be responsible for data manipulation of every user-changeable data
 */
class SettingsHelper(private val context: Context) : SharedPreferences.OnSharedPreferenceChangeListener {

    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val stationIds: MutableList<Long>
    private val keyPercent: String = context.getString(R.string.pref_key_percent)
    private val keyDragonHead: String = context.getString(R.string.pref_key_dragon_visible)

    var percentMode: Percent? = null
        private set

    init {
        PreferenceManager.setDefaultValues(context, R.xml.settings_general, false)
        preferences.registerOnSharedPreferenceChangeListener(this)
        stationIds = getList(preferences, KEY_STATION_ID_LIST)

        updatePercentMode()
    }

    val dragonVisible: Boolean
        get() = preferences.getBoolean(keyDragonHead, true)

    private fun updatePercentMode(sharedPreferences: SharedPreferences = preferences) {
        val defValue = context.getString(R.string.pref_percent_value_default)
        val string = sharedPreferences.getString(keyPercent, defValue)
        percentMode = Percent.find(context, string)
    }

    // This should be removed as soon as most of our users will update >= 197000
    @Deprecated(
            message = "This is old station list container, new one comes from domain module as part of SmokSmog class",
            level = DeprecationLevel.WARNING)
    var stationIdList: MutableList<Long>
        get() = stationIds
        set(longList) {
            val longsTemp = ArrayList(longList)
            stationIds.clear()
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
        when (key) {
            keyPercent -> updatePercentMode(sharedPreferences)
        }
    }

    companion object {

        private val EMPTY_STRING = "awd5ijsadf"
        private val SPLIT_CHAR = "%!@"
        private val KEY_STATION_ID_LIST = "KEY_STATION_ID_LIST"
    }
}
