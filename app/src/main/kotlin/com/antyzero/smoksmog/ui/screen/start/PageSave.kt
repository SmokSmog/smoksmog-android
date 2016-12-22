package com.antyzero.smoksmog.ui.screen.start

import android.content.Context
import android.content.SharedPreferences

class PageSave(context: Context) {

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    fun savePage(pageOrder: Int) {
        preferences.edit().putInt(KEY_ORDER, pageOrder).apply()
    }

    fun restorePage(): Int {
        return preferences.getInt(KEY_ORDER, 0)
    }

    companion object {

        private val TAG = PageSave::class.java.simpleName

        private val KEY_ORDER = "keyOrder"
    }
}
