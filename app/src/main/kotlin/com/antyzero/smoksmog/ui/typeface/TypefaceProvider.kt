package com.antyzero.smoksmog.ui.typeface


import android.content.Context
import android.graphics.Typeface

class TypefaceProvider(context: Context) {

    val default: Typeface

    init {
        default = Typeface.createFromAsset(context.assets, "fonts/Lato-Light.ttf")
    }
}
