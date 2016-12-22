package com.antyzero.smoksmog.ui.screen.start.item

import android.content.res.Resources
import android.view.View
import android.widget.TextView

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.settings.Percent
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.utils.TextUtils

import javax.inject.Inject

import butterknife.Bind
import butterknife.ButterKnife
import pl.malopolska.smoksmog.model.Particulate
import smoksmog.ui.IndicatorView

class ParticulateViewHolder(itemView: View) : ListViewHolder<Particulate>(itemView) {

    private val resources: Resources

    @Inject
    lateinit var settingsHelper: SettingsHelper

    @Bind(R.id.textViewName)
    internal var textViewName: TextView? = null
    @Bind(R.id.textViewMeasureDay)
    internal var textViewMeasureDay: TextView? = null
    @Bind(R.id.textViewMeasureHour)
    internal var textViewMeasureHour: TextView? = null
    @Bind(R.id.textViewTimeHour)
    internal var textViewTimeHour: TextView? = null
    @Bind(R.id.textViewTimeDay)
    internal var textViewTimeDay: TextView? = null
    @Bind(R.id.indicatorView)
    internal var indicatorView: IndicatorView? = null

    init {
        SmokSmogApplication[itemView.context].appComponent.inject(this)
        ButterKnife.bind(this, itemView)
        resources = itemView.context.resources
    }

    override fun bind(data: Particulate) {
        super.bind(data)
        textViewName!!.text = TextUtils.spannableSubscript(data.shortName)

        textViewMeasureDay!!.text = resources.getString(R.string.measurment, data.average, data.unit)
        textViewMeasureHour!!.text = resources.getString(R.string.measurment, data.value, data.unit)

        if (data.value > data.norm) {
            textViewTimeHour!!.setBackgroundResource(R.drawable.shape_oval_iron_border)
        } else {
            textViewTimeHour!!.setBackgroundResource(R.drawable.shape_oval_iron)
        }

        if (data.average > data.norm) {
            textViewTimeDay!!.setBackgroundResource(R.drawable.shape_oval_iron_border)
        } else {
            textViewTimeDay!!.setBackgroundResource(R.drawable.shape_oval_iron)
        }

        if (Percent.HOUR == settingsHelper!!.percentMode) {
            indicatorView!!.setValue(data.value / data.norm)
        } else {
            indicatorView!!.setValue(data.average / data.norm)
        }
    }
}
