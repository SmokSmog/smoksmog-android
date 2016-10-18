package com.antyzero.smoksmog.rules;

import android.support.test.rule.ActivityTestRule;

import com.squareup.spoon.Spoon;

import org.junit.rules.ExternalResource;

/**
 *
 */
public class SpoonRule extends ExternalResource {

    private final ActivityTestRule activityTestRule;

    public SpoonRule(ActivityTestRule activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public void screenshot(String tag) {
        try {
            Spoon.screenshot(activityTestRule.getActivity(), tag);
        } catch (Exception e) {
            System.err.println("Missing Spoon");
        }
    }

}
