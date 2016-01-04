package com.antyzero.smoksmog;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;

public class HistoryActivityTestRule extends MockedNetworkActivityTestRule<HistoryActivity> {

    public HistoryActivityTestRule() {
        super( HistoryActivity.class );
    }

    @Override
    protected Intent getActivityIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        try {
            return HistoryActivity.intent( context, 13 );
        } catch ( Exception e ) {
            throw new IllegalStateException( "Unable to create Intent" );
        }
    }
}
