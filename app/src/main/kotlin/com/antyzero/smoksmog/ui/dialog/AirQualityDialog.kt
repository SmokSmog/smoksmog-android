package com.antyzero.smoksmog.ui.dialog


import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView

import com.antyzero.smoksmog.R

import butterknife.Bind
import butterknife.ButterKnife

class AirQualityDialog : InfoDialog() {

    @Bind(R.id.textView)
    internal var textView: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.dialog_info_air_quality
    }

    override fun initView(view: View) {
        super.initView(view)
        ButterKnife.bind(this, view)
        textView!!.text = Html.fromHtml(getString(R.string.desc_air_quality))
        textView!!.movementMethod = LinkMovementMethod.getInstance()
    }
}
