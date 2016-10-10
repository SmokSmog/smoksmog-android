package com.antyzero.smoksmog.ui.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.fullscreen
import com.antyzero.smoksmog.ui.statusBarHeight
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_pick_station.*
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PickStation : BaseDragonActivity(), OnStationClick {

    @Inject
    lateinit var smokSmog: SmokSmog

    private val listStation: MutableList<Station> = mutableListOf()

    private lateinit var adapter: SimpleStationAdapter
    private lateinit var skipIds: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_station)
        fullscreen()

        SmokSmogApplication.get(this).appComponent
                .plus(ActivityModule(this))
                .inject(this)

        setSupportActionBar(toolbar)

        skipIds = intent.extras.getIntArray(EXTRA_SKIP_IDS)
        container.setPadding(0, statusBarHeight(), 0, 0)

        adapter = SimpleStationAdapter(listStation, this)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        }

        smokSmog.api.stations()
                .map { it.filter { skipIds.contains(it.id.toInt()).not() } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listStation.clear()
                    listStation.addAll(it)
                    adapter.notifyDataSetChanged()
                }
    }

    override fun click(station: Station) {
        endWithResult(station)
    }

    private fun endWithResult(station: Station) {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_STATION_ID, station.id)
        returnIntent.putExtra(EXTRA_STATION_NAME, station.name)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    companion object {

        private val EXTRA_STATION_ID = "extraStationId"
        private val EXTRA_STATION_NAME = "extraStationName"
        private val EXTRA_SKIP_IDS = "extraSkipIds"

        fun startForResult(activity: Activity, requestCode: Int) {
            this.startForResult(activity, requestCode, IntArray(0))
        }

        fun startForResult(activity: Activity, requestCode: Int, skipIds: IntArray = IntArray(0)) {
            val intent = Intent(activity, PickStation::class.java)
            intent.putExtra(EXTRA_SKIP_IDS, skipIds)
            activity.startActivityForResult(intent, requestCode)
        }

        @JvmStatic
        fun gatherResult(intent: Intent): Pair<Long, String> {
            val stationId = intent.getLongExtra(EXTRA_STATION_ID, -1)
            val stationName = intent.getStringExtra(EXTRA_STATION_NAME)
            return stationId to stationName
        }
    }
}