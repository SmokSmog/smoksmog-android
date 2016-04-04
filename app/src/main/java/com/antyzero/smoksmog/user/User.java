package com.antyzero.smoksmog.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * User management
 *
 * TODO extend in future, for now provide simple and random user ID
 */
public class User {

    private static final String PREFERENCES_USER = "USER";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String DEF_VALUE = "DEF_VALUE";

    private final String identifier;

    public User(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);

        if (!preferences.contains(KEY_USER_ID)) {
            identifier = createIdentifier();
            preferences.edit().putString(KEY_USER_ID, identifier).apply();
        } else {
            identifier = preferences.getString(KEY_USER_ID, DEF_VALUE);

            if (identifier.equals(DEF_VALUE)) {
                throw new IllegalStateException("Missing identifier for user");
            }
        }
    }

    @NonNull
    private String createIdentifier() {
        int value = new Random().nextInt(Integer.MAX_VALUE);
        int hashCode = String.valueOf(value).hashCode();
        return "ID-" + String.valueOf(Math.abs(hashCode));
    }

    @NonNull
    public String getIdentifier() {
        return identifier;
    }
}
