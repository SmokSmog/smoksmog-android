package com.antyzero.smoksmog.ui.screen.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.ListPreference;
import android.util.AttributeSet;

/**
 * Created by tornax on 05.01.16.
 */
public class SummaryListPreference extends ListPreference {

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public SummaryListPreference( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public SummaryListPreference( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public SummaryListPreference( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public SummaryListPreference( Context context ) {
        super( context );
    }


}
