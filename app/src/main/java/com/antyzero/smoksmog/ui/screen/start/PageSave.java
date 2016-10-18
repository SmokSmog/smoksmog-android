package com.antyzero.smoksmog.ui.screen.start;

import android.content.Context;
import android.content.SharedPreferences;

public class PageSave {

    private static final String TAG = PageSave.class.getSimpleName();

    private static final String KEY_ORDER = "keyOrder";

    private final SharedPreferences preferences;

    public PageSave(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public void savePage(int pageOrder) {
        preferences.edit().putInt(KEY_ORDER, pageOrder).apply();
    }

    public int restorePage() {
        return preferences.getInt(KEY_ORDER, 0);
    }
}
