package com.antyzero.smoksmog.dsl

import android.preference.Preference
import android.preference.PreferenceFragment


@Suppress("UNCHECKED_CAST")
fun <T : Preference> PreferenceFragment.findPreference(key: String): T? = this.findPreference(key) as T