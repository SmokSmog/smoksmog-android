package com.antyzero.smoksmog.ui.screen.history

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.annotation.IntRange
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.screen.ActivityModule

import javax.inject.Inject

import butterknife.Bind
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import smoksmog.logger.Logger

/**
 * Shows history chart
 */
class HistoryActivity : BaseDragonActivity() {

    @Inject
    lateinit var smokSmog: SmokSmog
    @Inject
    lateinit var errorReporter: ErrorReporter
    @Inject
    lateinit var logger: Logger

    @Bind(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    @Bind(R.id.recyclerViewCharts)
    internal var chartsRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmokSmogApplication[this].appComponent.plus(ActivityModule(this)).inject(this)

        val stationId = getStationIdExtra(intent)

        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        smokSmog!!.api.stationHistory(stationId)
                .compose(bindToLifecycle<Any>())
                .observeOn(AndroidSchedulers.mainThread())
                .cast(Station::class.java)
                .subscribe(
                        { station -> showHistory(station) }
                ) { throwable ->
                    val message = getString(R.string.error_unable_to_load_station_history)
                    errorReporter!!.report(message)
                    logger!!.i(TAG, message, throwable)
                }
    }

    private fun showHistory(station: Station?) {
        if (station == null || station.particulates == null) {
            //no valid data
            return
        }
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        val adapter = HistoryAdapter(station.particulates)
        chartsRecyclerView!!.layoutManager = GridLayoutManager(this, spanCount, LinearLayoutManager.VERTICAL, false)
        chartsRecyclerView!!.adapter = adapter
    }

    /**
     * @return Station ID if available or throws a [IllegalArgumentException]
     */
    private fun getStationIdExtra(intent: Intent?): Long {
        if (intent == null || !intent.hasExtra(STATION_ID_KEY)) {
            // TODO toast text should be in resources and tranlsted
            Toast.makeText(this, "Pokazanie historii było niemożliwe", Toast.LENGTH_SHORT).show()
            logger!!.e(TAG, "Unable to display History screen, missing start data")
            finish()
            return -1
        }
        return intent.getLongExtra(STATION_ID_KEY, -1)
    }

    companion object {
        private val STATION_ID_KEY = "station_id_key"
        private val TAG = "HistoryActivity"

        /**
         * Simple way to start HistoryActivity

         * @param context   for start
         * *
         * @param stationId to show
         */
        fun start(context: Context, @IntRange(from = 1) stationId: Long) {
            context.startActivity(intent(context, stationId))
        }

        /**
         * @param context for start
         * *
         * @param station to show
         * *
         * @return valid Intent to start HistoryActivity
         */
        fun intent(context: Context, station: Station): Intent {
            return intent(context, station.id)
        }

        /**
         * @param context   for start
         * *
         * @param stationId to show
         * *
         * @return valid Intent to start HistoryActivity
         */
        fun intent(context: Context, @IntRange(from = 1) stationId: Long): Intent {
            val intent = Intent(context, HistoryActivity::class.java)
            fillIntent(intent, stationId)
            return intent
        }

        @VisibleForTesting
        fun fillIntent(intent: Intent, @IntRange(from = 1) stationId: Long): Intent {
            intent.putExtra(STATION_ID_KEY, stationId)
            return intent
        }
    }
}