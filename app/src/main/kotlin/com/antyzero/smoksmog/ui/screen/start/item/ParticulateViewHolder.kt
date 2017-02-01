package com.antyzero.smoksmog.ui.screen.start.item


import android.content.res.Resources
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.findViewById
import com.antyzero.smoksmog.settings.Percent
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.utils.TextUtils
import pl.malopolska.smoksmog.model.Particulate
import pl.malopolska.smoksmog.model.ParticulateEnum
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex
import smoksmog.ui.IndicatorView
import javax.inject.Inject

class ParticulateViewHolder(itemView: View) : ListViewHolder<Particulate>(itemView) {

    private val resources: Resources = itemView.context.resources

    @Inject lateinit var settingsHelper: SettingsHelper

    val textViewName: TextView = findViewById(R.id.textViewName) as TextView
    val textViewMeasureDay: TextView = findViewById(R.id.textViewMeasureDay) as TextView
    val textViewMeasureHour: TextView = findViewById(R.id.textViewMeasureHour) as TextView
    val textViewTimeHour: TextView = findViewById(R.id.textViewTimeHour) as TextView
    val textViewTimeDay: TextView = findViewById(R.id.textViewTimeDay) as TextView
    val indicatorView: IndicatorView = findViewById(R.id.indicatorView) as IndicatorView
    val particulateIndex: ImageView = findViewById(R.id.particulateIndex) as ImageView

    init {
        SmokSmogApplication[itemView.context].appComponent.inject(this)
    }

    override fun bind(data: Particulate) {
        super.bind(data)

        textViewName.text = TextUtils.spannableSubscript(data.shortName)

        textViewMeasureDay.text = resources.getString(R.string.measurment, data.average, data.unit)
        textViewMeasureHour.text = resources.getString(R.string.measurment, data.value, data.unit)

        if (data.value > data.norm && ParticulateEnum.PM25 != data.enum) {
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

        if(ParticulateEnum.PM25 == data.enum){
            indicatorView.visibility = GONE
            textViewMeasureDay.visibility = GONE
            textViewTimeDay.visibility = GONE
        }

        particulateIndex.setColorFilter(AirQuality.findByValue(AirQualityIndex.calculate(data)).getColor(context))
    }
}
