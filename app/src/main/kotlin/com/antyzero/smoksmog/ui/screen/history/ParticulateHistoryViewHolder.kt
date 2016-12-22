package com.antyzero.smoksmog.ui.screen.history

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.Bind
import butterknife.ButterKnife
import com.antyzero.smoksmog.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import pl.malopolska.smoksmog.model.History
import pl.malopolska.smoksmog.model.Particulate
import java.util.*

public class ParticulateHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @Bind(R.id.lineChart)
    var chart: LineChart? = null

    init {
        ButterKnife.bind(this, itemView)
        val resources = itemView.resources
        chart!!.setBackgroundColor(resources.getColor(android.R.color.transparent))
        chart!!.setGridBackgroundColor(resources.getColor(android.R.color.transparent))
        chart!!.setDescriptionColor(resources.getColor(R.color.text_light))

    }

    fun bind(particulate: Particulate) {
        val historyList = particulate.values
        Collections.sort(historyList) { history1, history2 -> if (history1.date.isAfter(history2.date)) 1 else -1 }

        val lineColor = itemView.context.resources.getColor(R.color.primary)
        val fillColor = itemView.context.resources.getColor(R.color.accent)
        val lineData = getLineData(historyList, lineColor, fillColor)

        chart!!.data = lineData
        chart!!.setDescription(particulate.name + " (" + particulate.shortName + ")")
        chart!!.setDescriptionTextSize(12f)

        val normLimitLine = createLimitLine(particulate)
        chart!!.axisRight.isEnabled = false
        chart!!.axisLeft.removeAllLimitLines()
        chart!!.axisLeft.addLimitLine(normLimitLine)
        chart!!.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        chart!!.axisLeft.axisMaxValue = getYAxisMac(particulate, lineData)

        chart!!.legend.isEnabled = false

        chart!!.invalidate()
    }

    private fun getYAxisMac(particulate: Particulate, lineData: LineData): Float {
        val baseMax = if (particulate.norm > lineData.yMax) particulate.norm else lineData.yMax
        return baseMax + baseMax / 100 * 10
    }

    private fun getLineData(historyList: List<History>, lineColor: Int, fillColor: Int): LineData {
        val lineData = LineData()
        val entries = ArrayList<Entry>()

        for (i in historyList.indices) {
            val history = historyList[i]
            val entry = Entry(history.value, i)
            entries.add(entry)

            //add short day of week (eg. "Tue ")
            lineData.addXValue(history.date.toString("EEE"))
        }
        val lineDataSet = LineDataSet(entries, null)
        lineDataSet.setCircleColorHole(lineColor)
        lineDataSet.setCircleColor(lineColor)
        lineDataSet.color = lineColor
        lineDataSet.valueTextSize = 10f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillAlpha = 50
        lineDataSet.fillColor = fillColor
        lineDataSet.setDrawCubic(true)

        lineData.addDataSet(lineDataSet)
        lineData.isHighlightEnabled = false
        lineData.setDrawValues(false)
        return lineData
    }

    private fun createLimitLine(particulate: Particulate): LimitLine {
        val normLimitLine = LimitLine(particulate.norm, itemView.context.getString(R.string.label_norm))
        normLimitLine.lineWidth = 1f
        normLimitLine.lineColor = Color.DKGRAY
        normLimitLine.textColor = Color.DKGRAY
        normLimitLine.textSize = 10f
        normLimitLine.enableDashedLine(10f, 10f, 0f)
        normLimitLine.labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
        normLimitLine.textSize = 10f
        return normLimitLine
    }
}
