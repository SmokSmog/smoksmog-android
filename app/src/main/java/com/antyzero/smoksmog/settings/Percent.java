package com.antyzero.smoksmog.settings;

import android.content.Context;
import android.support.annotation.StringRes;

import com.antyzero.smoksmog.R;

/**
 * Indicates from which period measurement should be taken
 */
public enum Percent {

    HOUR(R.string.pref_percent_value_hour), DAY(R.string.pref_percent_value_day);

    private final int value;

    Percent(@StringRes int value) {
        this.value = value;
    }

    public static Percent find(Context context, String value) {

        for (Percent percent : values()) {
            if (context.getString(percent.value).equals(value)) {
                return percent;
            }
        }
        throw new IllegalArgumentException("Unable to find proper enum value for \"" + value + "\"");
    }
}
