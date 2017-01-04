package com.antyzero.smoksmog.settings

import android.content.Context
import android.support.annotation.StringRes

import com.antyzero.smoksmog.R

/**
 * Indicates from which period measurement should be taken
 */
enum class Percent constructor(@StringRes private val value: Int) {

    HOUR(R.string.pref_percent_value_hour), DAY(R.string.pref_percent_value_day);

    companion object {

        fun find(context: Context, value: String): Percent {

            values()
                    .filter { context.getString(it.value) == value }
                    .forEach { return it }

            throw IllegalArgumentException("Unable to find proper enum value for \"" + value + "\"")
        }
    }
}
