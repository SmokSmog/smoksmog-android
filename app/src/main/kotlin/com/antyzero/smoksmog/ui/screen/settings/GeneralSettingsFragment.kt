package com.antyzero.smoksmog.ui.screen.settings

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.CheckBoxPreference
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmog
import com.antyzero.smoksmog.dsl.activityComponent
import com.antyzero.smoksmog.dsl.findPreference
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.permission.PermissionHelper
import com.antyzero.smoksmog.storage.model.Item
import com.antyzero.smoksmog.ui.BasePreferenceFragment
import com.antyzero.smoksmog.ui.screen.FragmentModule
import smoksmog.logger.Logger
import javax.inject.Inject

class GeneralSettingsFragment : BasePreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var errorReporter: ErrorReporter
    @Inject lateinit var logger: Logger
    @Inject lateinit var permissionHelper: PermissionHelper

    lateinit private var keyStationClosest: String
    lateinit private var preferenceStationCloset: CheckBoxPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_general)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        keyStationClosest = getString(R.string.pref_key_station_closest)
        preferenceStationCloset = findPreference<CheckBoxPreference>(keyStationClosest) as CheckBoxPreference
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activityComponent(activity)
                .plus(FragmentModule(this))
                .inject(this)
    }

    override fun onDestroy() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val isClosestStationKey = keyStationClosest == key
            val hasLocationPermission = permissionHelper.isGrantedAccessCoarseLocation
            if (isClosestStationKey && !hasLocationPermission) {
                requestPermissions(arrayOf(ACCESS_COARSE_LOCATION), REQUEST_CODE_ASK_PERMISSION)
            }
        }

        // We assume that uses will grant permission, in case not we should revert settings
        when (key) {
            keyStationClosest -> {
                if (sharedPreferences.getBoolean(key, false)) {
                    smokSmog.storage.add(Item.Nearest())
                } else {
                    smokSmog.storage.removeById(Item.Nearest().id)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSION ->

                // if not granted keep closest station not visible
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    preferenceStationCloset.isChecked = false
                    smokSmog.storage.removeById(Item.Nearest().id)
                    errorReporter.report("Location permission not granted")
                }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    companion object {

        private val REQUEST_CODE_ASK_PERMISSION = 123
    }
}
