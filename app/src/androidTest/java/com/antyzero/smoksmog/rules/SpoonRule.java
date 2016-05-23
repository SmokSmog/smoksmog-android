package com.antyzero.smoksmog.rules;

import android.app.Activity;

import com.squareup.spoon.Spoon;

import org.junit.rules.ExternalResource;

/**
 *
 */
public class SpoonRule extends ExternalResource {

    private final Activity activity;

    public SpoonRule(Activity activity) {
        this.activity = activity;
    }

    public void screenshot(String tag){
        Spoon.screenshot(activity, tag);
    }

}
