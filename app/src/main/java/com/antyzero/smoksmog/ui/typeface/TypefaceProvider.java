package com.antyzero.smoksmog.ui.typeface;


import android.content.Context;
import android.graphics.Typeface;

public class TypefaceProvider {

    private final Typeface defaultTypeface;

    public TypefaceProvider( Context context ) {
        defaultTypeface = Typeface.createFromAsset( context.getAssets(), "fonts/Lato-Light.ttf" );
    }

    public Typeface getDefault() {
        return defaultTypeface;
    }
}
