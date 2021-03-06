package com.antyzero.smoksmog.ui.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.SearchView
import android.view.Menu
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.fullscreen
import com.antyzero.smoksmog.ui.BaseDragonActivity
import kotlinx.android.synthetic.main.activity_pick_station.*
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PickStationActivity : BaseDragonActivity(), OnStationClick, SearchView.OnQueryTextListener {

    @Inject lateinit var api: Api

    private val listStation: MutableList<Station> = mutableListOf()
    private val allStations: MutableList<Station> = mutableListOf()

    private lateinit var adapter: SimpleStationAdapter
    private lateinit var skipIds: LongArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_station)
        fullscreen()

        SmokSmogApplication[this].appComponent
                .plus(ActivityModule(this))
                .inject(this)

        setSupportActionBar(toolbar)

        skipIds = intent?.extras?.getLongArray(EXTRA_SKIP_IDS) ?: LongArray(0)
        adapter = SimpleStationAdapter(listStation, this)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        }

        api.stations()
                .flatMap { Observable.from(it) }
                .filter { skipIds.contains(it.id).not() }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    allStations.addAll(it.sortedBy { it.name })
                    listStation.addAll(allStations)
                    adapter.notifyDataSetChanged()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pick_station, menu)
        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.search)) as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        listStation.clear()
        listStation.addAll(allStations.filter { it.name.contains(newText, true) })
        adapter.notifyDataSetChanged()
        return false
    }

    override fun onQueryTextSubmit(query: String?) = false

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
            this.startForResult(activity, requestCode, LongArray(0))
        }

        fun startForResult(activity: Activity, requestCode: Int, skipIds: LongArray = LongArray(0)) {
            val intent = Intent(activity, PickStationActivity::class.java)
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