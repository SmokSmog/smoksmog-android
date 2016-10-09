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
import kotlinx.android.synthetic.main.activity_pick_station.*
import kotlinx.android.synthetic.main.activity_base.*
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PickStation : BaseDragonActivity(), OnStationClick {

    @Inject
    lateinit var smokSmog: SmokSmog

    private val listStation: MutableList<Station> = mutableListOf()

    private lateinit var adapter: SimpleStationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_station)
        fullscreen()

        SmokSmogApplication.get(this).appComponent
                .plus(ActivityModule(this))
                .inject(this)

        setSupportActionBar(toolbar)

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listStation.clear()
                    listStation.addAll(it)
                    adapter.notifyDataSetChanged()
                }
    }

    override fun click(station: Station) {
        endWithResult(station.id)
    }

    private fun endWithResult(result: Long) {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_STATION_ID, result)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    companion object {

        private val EXTRA_STATION_ID = "extraStationId"

        fun startForResult(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, PickStation::class.java), requestCode)
        }

        @JvmStatic
        fun gatherResult(intent: Intent): Long {
            return intent.getLongExtra(EXTRA_STATION_ID, -1)
        }
    }
}