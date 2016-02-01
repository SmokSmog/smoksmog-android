package com.antyzero.smoksmog.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;

/**
 * Various tools for positioning manipulation
 */
public class DimenUtils {

    private DimenUtils() {
        throw new IllegalAccessError( "Utils class" );
    }

    public static int getNavBarHeight( Context context, @DimenRes int defaultRes ) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "navigation_bar_height", "dimen", "android" );
        return resources.getDimensionPixelSize( resourceId > 0 ? resourceId : defaultRes );
    }
}
