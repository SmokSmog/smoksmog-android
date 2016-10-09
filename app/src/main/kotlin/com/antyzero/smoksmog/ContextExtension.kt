package com.antyzero.smoksmog

import android.content.Context
import android.widget.Toast

fun Context.toast(chars: CharSequence, duration: Int) = Toast.makeText(this, chars, duration).show()