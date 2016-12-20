package com.antyzero.smoksmog.ui.widget

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.antyzero.smoksmog.appComponent
import com.antyzero.smoksmog.appWidgetManager
import com.antyzero.smoksmog.dsl.tag
import pl.malopolska.smoksmog.SmokSmog
import smoksmog.logger.Logger
import javax.inject.Inject


class StationWidgetService : IntentService(StationWidgetService::class.java.simpleName) {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var widgetData: StationWidgetData
    @Inject lateinit var logger: Logger

    lateinit var appWidgetManager: AppWidgetManager

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        this.appWidgetManager = appWidgetManager()
    }

    override fun onHandleIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_WIDGET_ID)) {
            val widgetId = intent.getIntExtra(EXTRA_WIDGET_ID, Int.MIN_VALUE)
            try {
                val stationId = widgetData.widgetStationId(widgetId)
                val station = smokSmog.api.station(stationId).toBlocking().first()
                StationWidget.updateWidget(widgetId, this, appWidgetManager, station)
            } catch (e: Exception) {
                logger.w(tag(), "Unable to update widget $widgetId", e)
            }
        }
    }

    companion object {

        internal val EXTRA_WIDGET_ID = "widgetId"

        fun update(context: Context, widgetId: Int) {
            val intent = Intent(context, StationWidgetService::class.java)
            intent.putExtra(EXTRA_WIDGET_ID, widgetId)
            context.startService(intent)
        }

        fun updateAll(context: Context) {
            context.appWidgetManager().getAppWidgetIds(ComponentName(context, StationWidget::class.java)).forEach {
                StationWidgetService.update(context, it)
            }
        }
    }
}