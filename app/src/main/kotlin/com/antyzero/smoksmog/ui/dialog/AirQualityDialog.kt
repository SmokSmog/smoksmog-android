package com.antyzero.smoksmog.ui.dialog


import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.dsl.compatFromHtml

class AirQualityDialog : InfoDialog() {

    override fun getLayoutId(): Int = R.layout.dialog_info_air_quality

    override fun initView(view: View) {
        super.initView(view)

        val textView = view.findViewById(R.id.textView) as TextView

        textView.compatFromHtml(R.string.desc_air_quality)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
