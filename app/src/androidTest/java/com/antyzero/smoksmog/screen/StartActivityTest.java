package com.antyzero.smoksmog.screen;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.rules.RxSchedulerTestRule;
import com.antyzero.smoksmog.rules.SpoonRule;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class StartActivityTest {

    @Rule
    public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();
    private final ActivityTestRule<StartActivity> activityTestRule = new MockedNetworkActivityTestRule<>(StartActivity.class, false, true);
    private final SpoonRule spoonRule = new SpoonRule(activityTestRule);
    @Rule
    public final TestRule testRule = RuleChain.outerRule(activityTestRule).around(spoonRule);

    @Test
    public void checkCreation() {
        spoonRule.screenshot("Creation");
    }

    // @Test
    public void addStation() {

        // Given
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // When
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.action_manage_stations)).perform(click());
        onView(withId(R.id.fab)).perform(click());
    }
}
