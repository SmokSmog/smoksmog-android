package com.antyzero.smoksmog.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.tag
import com.antyzero.smoksmog.toast
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import smoksmog.logger.Logger
import javax.inject.Inject

class StationWidget : AppWidgetProvider() {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var logger: Logger
    @Inject lateinit var widgetData: StationWidgetData

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        SmokSmogApplication.get(context).appComponent.inject(this)

        appWidgetIds.forEach {
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

        }
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        context!!.toast(appWidgetId.toString())
    }

    companion object {

        fun updateWidget(widgetId: Int, context: Context, appWidgetManager: AppWidgetManager, station: Station) {
            val views = RemoteViews(context.packageName, R.layout.widget_station)
            views.setTextViewText(R.id.textViewStation, station.name)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}