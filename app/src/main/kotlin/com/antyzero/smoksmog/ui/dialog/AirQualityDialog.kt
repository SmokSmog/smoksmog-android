package com.antyzero.smoksmog.ui.dialog


import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.antyzero.smoksmog.R
import kotlinx.android.synthetic.main.dialog_info_air_quality.*

class AirQualityDialog : InfoDialog() {

    override fun getLayoutId(): Int = R.layout.dialog_info_air_quality

    override fun initView(view: View) {
        super.initView(view)
        textView.text = Html.fromHtml(getString(R.string.desc_air_quality))
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
