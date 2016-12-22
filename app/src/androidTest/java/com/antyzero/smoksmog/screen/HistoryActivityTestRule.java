package com.antyzero.smoksmog.screen;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;

public class HistoryActivityTestRule extends MockedNetworkActivityTestRule<HistoryActivity> {

    public HistoryActivityTestRule() {
        super(HistoryActivity.class);
    }

    public HistoryActivityTestRule(boolean initialTouchMode) {
        super(HistoryActivity.class, initialTouchMode);
    }

    public HistoryActivityTestRule(boolean initialTouchMode, boolean launchActivity) {
        super(HistoryActivity.class, initialTouchMode, launchActivity);
    }

    @Override
    protected Intent getActivityIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        try {
            return HistoryActivity.Companion.intent(context, 13);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create Intent");
        }
    }
}
