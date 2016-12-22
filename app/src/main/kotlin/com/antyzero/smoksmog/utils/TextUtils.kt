package com.antyzero.smoksmog.utils


import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.SubscriptSpan

class TextUtils private constructor() {

    init {
        throw IllegalAccessError("Utils class")
    }

    companion object {

        fun spannableSubscript(originalText: String): CharSequence {

            val builder = SpannableStringBuilder()

            for (i in 0..originalText.length - 1) {
                val code = originalText.codePointAt(i)
                if (code >= 8320 && code <= 8329) {
                    builder.append(String(Character.toChars(code - 8272)))
                    builder.setSpan(SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.setSpan(RelativeSizeSpan(0.55f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                } else {
                    builder.append(originalText[i])
                }
            }

            return builder
        }
    }
}
