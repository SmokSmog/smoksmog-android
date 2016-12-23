package com.antyzero.smoksmog.ui.screen.start.item


import android.content.res.Resources
import android.view.View
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.settings.Percent
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.utils.TextUtils
import pl.malopolska.smoksmog.model.Particulate
import com.antyzero.smoksmog.dsl.findViewById
import smoksmog.ui.IndicatorView
import javax.inject.Inject

class ParticulateViewHolder(itemView: View) : ListViewHolder<Particulate>(itemView) {

    private val resources: Resources

    @Inject lateinit var settingsHelper: SettingsHelper

    val textViewName: TextView
    val textViewMeasureDay: TextView
    val textViewMeasureHour: TextView
    val textViewTimeHour: TextView
    val textViewTimeDay: TextView
    val indicatorView: IndicatorView

    init {
        SmokSmogApplication[itemView.context].appComponent.inject(this)
        resources = itemView.context.resources

        textViewName = findViewById(R.id.textViewName) as TextView
        textViewMeasureDay = findViewById(R.id.textViewMeasureDay) as TextView
        textViewMeasureHour = findViewById(R.id.textViewMeasureHour) as TextView
        textViewTimeHour = findViewById(R.id.textViewTimeHour) as TextView
        textViewTimeDay = findViewById(R.id.textViewTimeDay) as TextView
        indicatorView = findViewById(R.id.indicatorView) as IndicatorView
    }

    override fun bind(data: Particulate) {
        super.bind(data)
        textViewName.text = TextUtils.spannableSubscript(data.shortName)

        textViewMeasureDay.text = resources.getString(R.string.measurment, data.average, data.unit)
        textViewMeasureHour.text = resources.getString(R.string.measurment, data.value, data.unit)

        if (data.value > data.norm) {
            textViewTimeHour.setBackgroundResource(R.drawable.shape_oval_iron_border)
        } else {
            textViewTimeHour.setBackgroundResource(R.drawable.shape_oval_iron)
        }

        if (data.average > data.norm) {
            textViewTimeDay.setBackgroundResource(R.drawable.shape_oval_iron_border)
        } else {
            textViewTimeDay.setBackgroundResource(R.drawable.shape_oval_iron)
        }

        if (Percent.HOUR == settingsHelper.percentMode) {
            indicatorView.setValue(data.value / data.norm)
        } else {
            indicatorView.setValue(data.average / data.norm)
        }
    }
}
