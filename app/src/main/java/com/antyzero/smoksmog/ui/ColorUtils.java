package com.antyzero.smoksmog.ui;

/**
 * Created by iwopolanski on 25.01.16.
 */
public class ColorUtils {

    private ColorUtils() {
        throw new IllegalStateException("Utils class");
    }
/*
    public static int contrastColor( int backgroundColor ){

        Color color = new Color();

        int d = 0;

        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - ( 0.299 * color.R + 0.587 * color.G + 0.114 * color.B)/255;

        if (a < 0.5)
            d = 0; // bright colors - black font
        else
            d = 255; // dark colors - white font

        return  Color.FromArgb(d, d, d);
    }
*/
}
