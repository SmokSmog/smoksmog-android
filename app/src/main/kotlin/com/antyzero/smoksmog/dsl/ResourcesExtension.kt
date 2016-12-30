package com.antyzero.smoksmog.dsl

import android.content.res.Resources
import android.support.annotation.ColorRes


fun Resources.getCompatColor(@ColorRes colorId: Int) {
    this.getColor(colorId)
}