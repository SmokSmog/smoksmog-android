package com.antyzero.smoksmog.screen;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.rules.RxSchedulerTestRule;
import com.antyzero.smoksmog.rules.SpoonRule;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HistoryActivityTest {

    private final ActivityTestRule<HistoryActivity> activityTestRule = new HistoryActivityTestRule(true, false);
    private final SpoonRule spoonRule = new SpoonRule(activityTestRule);
    @Rule
    public final TestRule testRule = RuleChain.outerRule(activityTestRule).around(spoonRule);
    @Rule
    public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();

    @Test
    public void checkCreation() {

        // given
        activityTestRule.launchActivity(HistoryActivity.fillIntent(new Intent(), 13));

        // when
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // then
        spoonRule.screenshot("Created");
    }
}
