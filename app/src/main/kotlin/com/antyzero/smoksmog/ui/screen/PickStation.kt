package com.antyzero.smoksmog.ui.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_station.*
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PickStation : BaseActivity() {

    @Inject
    lateinit var smokSmog: SmokSmog

    private val listStation: MutableList<Station> = mutableListOf()

    private lateinit var adapter: SimpleStationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_station)

        SmokSmogApplication.get(this).appComponent
                .plus(ActivityModule(this))
                .inject(this)

        setSupportActionBar(toolbar)

        adapter = SimpleStationAdapter(listStation)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.adapter = adapter

        smokSmog.api.stations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listStation.clear()
                    listStation.addAll(it)
                    adapter.notifyDataSetChanged()
                }
    }

    private fun endWithResult(result: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_STATION_ID, result)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    companion object {

        private val EXTRA_STATION_ID = "asdkjh"

        fun startForResult(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, PickStation::class.java), requestCode)
        }

        @JvmStatic
        fun gatherResult(intent: Intent): Int {
            return intent.getIntExtra(EXTRA_STATION_ID, -1)
        }
    }
}