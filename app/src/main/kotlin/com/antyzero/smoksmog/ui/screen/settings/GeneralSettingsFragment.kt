package com.antyzero.smoksmog.ui.screen.settings

import android.Manifest
import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.support.annotation.StringRes
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.ui.BasePreferenceFragment
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.FragmentModule
import com.crashlytics.android.answers.Answers
import pl.malopolska.smoksmog.RestClient
import smoksmog.logger.Logger
import javax.inject.Inject

/**

 */
class GeneralSettingsFragment : BasePreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    //<editor-fold desc="Dagger">
    @Inject
    lateinit var restClient: RestClient
    @Inject
    lateinit var errorReporter: ErrorReporter
    @Inject
    lateinit var logger: Logger
    @Inject
    lateinit var answers: Answers
    @Inject
    lateinit var settingsHelper: SettingsHelper
    //</editor-fold>

    private var keyStationClosest: String? = null
    private var preferenceStationCloset: CheckBoxPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_general)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        keyStationClosest = getString(R.string.pref_key_station_closest)
        preferenceStationCloset = findPreference(keyStationClosest) as CheckBoxPreference
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        SmokSmogApplication[activity].appComponent
                .plus(ActivityModule(activity))
                .plus(FragmentModule(this))
                .inject(this)
    }

    override fun onDestroy() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    private fun findPreference(@StringRes stringResId: Int): Preference {
        return findPreference(getString(stringResId))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        // Check permission - preference if changed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val accessCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
            val isClosestStationKey = keyStationClosest == key
            val hasLocationPermission = activity.checkSelfPermission(accessCoarseLocation) != PackageManager.PERMISSION_GRANTED
            if (isClosestStationKey && hasLocationPermission) {
                requestPermissions(arrayOf(accessCoarseLocation), REQUEST_CODE_ASK_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSION ->
                // if not granted keep closest station not visible
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val sharedPreferences = preferenceManager.sharedPreferences
                    sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
                    preferenceStationCloset!!.isChecked = false
                    sharedPreferences.registerOnSharedPreferenceChangeListener(this)
                    // TODO localize
                    errorReporter.report("Location permission not granted")
                }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {

        private val REQUEST_CODE_ASK_PERMISSION = 123

        fun create(): GeneralSettingsFragment {
            return GeneralSettingsFragment()
        }
    }
}
