package com.antyzero.smoksmog.ui.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import com.antyzero.smoksmog.activityComponent
import com.antyzero.smoksmog.appWidgetManager
import com.antyzero.smoksmog.tag
import com.antyzero.smoksmog.toast
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.screen.PickStation
import pl.malopolska.smoksmog.SmokSmog
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import smoksmog.logger.Logger
import javax.inject.Inject


class StationWidgetConfigureActivity : BaseDragonActivity() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var stationWidgetData: StationWidgetData
    @Inject lateinit var smokSmog: SmokSmog

    private val PICK_STATION_REQUEST: Int = 8976
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        setResult(Activity.RESULT_CANCELED)

        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            toast("Błąd dodawania widżetu") // TODO translate
            logger.w(tag(), "Error while creating, invalid widget ID")
            finish()
            return
        }

        PickStation.startForResult(this, PICK_STATION_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (PICK_STATION_REQUEST == requestCode && resultCode == Activity.RESULT_OK && data != null) {
            addStationWidget(PickStation.gatherResult(data))
        } else {
            toast("Błąd w wyborze stacji") // TODO translate
            logger.i(tag(), "Station not picked up")
            finish()
        }
    }

    private fun addStationWidget(gatherResult: Pair<Long, String>) {
        stationWidgetData.addWidget(appWidgetId, gatherResult.first)
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)

        smokSmog.api.station(gatherResult.first)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { StationWidget.updateWidget(appWidgetId, this, appWidgetManager(), it) },
                        {
                            toast("Unable to get widget data") // TODO translate
                            logger.w(tag(), "Unable to get widget data")
                            finish()
                        },
                        {finish()}
                )
    }
}