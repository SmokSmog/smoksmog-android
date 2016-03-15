package com.antyzero.smoksmog.utils;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;

public class TextUtils {

    private TextUtils() {
        throw new IllegalAccessError( "Utils class" );
    }

    /**
     * @param originalText
     * @return
     */
    public static CharSequence spannableSubscript( String originalText ) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        for ( int i = 0; i < originalText.length(); i++ ) {
            int code = originalText.codePointAt( i );
            if ( code >= 8320 && code <= 8329 ) {
                builder.append( new String( Character.toChars( code - 8272 ) ) );
                builder.setSpan( new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                builder.setSpan( new RelativeSizeSpan( 0.55f ), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            } else {
                builder.append( originalText.charAt( i ) );
            }
        }

        return builder;
    }
}
