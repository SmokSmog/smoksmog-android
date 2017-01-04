package com.antyzero.smoksmog.user

import android.content.Context
import java.util.*

/**
 * User management
 */
class User(context: Context) {

    val identifier: String

    init {

        val preferences = context.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE)

        if (!preferences.contains(KEY_USER_ID)) {
            identifier = createIdentifier()
            preferences.edit().putString(KEY_USER_ID, identifier).apply()
        } else {
            identifier = preferences.getString(KEY_USER_ID, DEF_VALUE)

            if (identifier == DEF_VALUE) {
                throw IllegalStateException("Missing identifier for user")
            }
        }
    }

    private fun createIdentifier(): String {
        val value = Random().nextInt(Integer.MAX_VALUE)
        val hashCode = value.toString().hashCode()
        return "ID-" + Math.abs(hashCode).toString()
    }

    companion object {

        private val PREFERENCES_USER = "USER"
        private val KEY_USER_ID = "KEY_USER_ID_V3"
        private val DEF_VALUE = "DEF_VALUE"
    }
}
