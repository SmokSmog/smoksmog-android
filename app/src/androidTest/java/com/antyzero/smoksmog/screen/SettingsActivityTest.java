package com.antyzero.smoksmog.screen;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.rules.RxSchedulerTestRule;
import com.antyzero.smoksmog.rules.SpoonRule;
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsActivityTest {

    @Rule
    public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();
    private final ActivityTestRule<SettingsActivity> activityTestRule = new MockedNetworkActivityTestRule<>(SettingsActivity.class);
    private final SpoonRule spoonRule = new SpoonRule(activityTestRule);
    @Rule
    public final TestRule testRule = RuleChain.outerRule(activityTestRule).around(spoonRule);

    @Test
    public void checkCreation() {

        // Given
        activityTestRule.getActivity();

        // When
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Then
        spoonRule.screenshot("Created");
    }
}
