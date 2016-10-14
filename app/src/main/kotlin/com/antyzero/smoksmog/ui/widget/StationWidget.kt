package com.antyzero.smoksmog.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.toast
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex
import smoksmog.logger.Logger
import javax.inject.Inject

class StationWidget : AppWidgetProvider() {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var logger: Logger
    @Inject lateinit var widgetData: StationWidgetData

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        SmokSmogApplication.get(context).appComponent.inject(this)

        appWidgetIds.forEach {
            StationWidgetService.update(context, it)
            // TODO seems like doing network calls here is not working
            /*
            val widgetId = it
            val stationId = widgetData.widgetStationId(it)

            smokSmog.api.station(stationId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            Action1 {
                                updateWidget(widgetId, context, appWidgetManager, it)
                            },
                            Action1 {
                                logger.w(tag(), "Unable to update widget $widgetId", it)
                            }
                    )
                    */
        }
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        context!!.toast(appWidgetId.toString())
    }

    companion object {

        fun updateWidget(widgetId: Int, context: Context, appWidgetManager: AppWidgetManager, station: Station) {

            val airQualityIndex = AirQualityIndex.calculate(station)
            val airQuality = AirQuality.findByValue(airQualityIndex)

            val views = RemoteViews(context.packageName, R.layout.widget_station)

            views.setTextViewText(R.id.textViewStation, station.name)
            views.setTextViewText(R.id.textViewAirQuality, airQualityIndex.format(1))

            views.setTextColor(R.id.textViewAirQuality, airQuality.getColor(context))
            views.setBackgroundColor(R.id.airIndicator1, airQuality.getColor(context))
            views.setBackgroundColor(R.id.airIndicator2, airQuality.getColor(context))

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}

private fun Double.format(digits: Int): CharSequence {
    return java.lang.String.format("%.${digits}f", this)
}
