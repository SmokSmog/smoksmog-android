package com.antyzero.smoksmog.time;


import android.content.Context;

import java.util.Locale;

public class CountdownProvider {

    private static final Locale LOCALE_POLISH = new Locale("pl", "PL");

    private final Context context;

    public CountdownProvider(Context context) {
        this.context = context;
    }

    public String get(int seconds) {

        Locale locale = context.getResources().getConfiguration().locale;

        if (LOCALE_POLISH.equals(locale)) {
            return new PolishCountdown().get(seconds);
        } else {
            return new EnglishCountdown().get(seconds);
        }
    }
}
