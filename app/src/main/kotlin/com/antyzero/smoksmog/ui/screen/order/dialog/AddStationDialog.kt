package com.antyzero.smoksmog.ui.screen.order.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule

import java.util.ArrayList

import javax.inject.Inject

import butterknife.Bind
import butterknife.ButterKnife
import pl.malopolska.smoksmog.RestClient
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers
import smoksmog.logger.Logger


class AddStationDialog : DialogFragment(), StationDialogAdapter.StationListener {
    private val stationList = ArrayList<Station>()
    private val stationIdsNotToShow = ArrayList<Long>()
    @Inject
    lateinit var restClient: RestClient
    @Inject
    lateinit var logger: Logger
    @Bind(R.id.recyclerView)
    internal var recyclerView: RecyclerView? = null
    private var stationListener: StationDialogAdapter.StationListener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        if (activity !is StationDialogAdapter.StationListener) {
            throw IllegalStateException("Activity needs to implement StationListener")
        }

        if (!arguments.containsKey(KEY_STATION_IDS)) {
            throw IllegalStateException("Missing station id values")
        }

        val stationIdsArray = arguments.getLongArray(KEY_STATION_IDS)
        if (stationIdsArray != null) {
            for (aStationIdsArray in stationIdsArray) {
                stationIdsNotToShow.add(aStationIdsArray)
            }
        }

        stationListener = activity as StationDialogAdapter.StationListener?

        SmokSmogApplication[activity]
                .appComponent
                .plus(ActivityModule(activity))
                .plus(SupportFragmentModule(this@AddStationDialog))
                .inject(this)

    }

    override fun onStart() {
        super.onStart()
        stationList.clear()
        restClient.stations()
                .flatMap(Func1<List<Station>, Observable<Station>> { null })
                .filter { station -> !stationIdsNotToShow.contains(station.id) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { station -> stationList.add(station) },
                        { throwable -> logger.e(TAG, "Unable to load stations", throwable) }
                ) { recyclerView!!.adapter.notifyDataSetChanged() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.view_recyclerview, recyclerView, false)

        ButterKnife.bind(this, view)
        recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = StationDialogAdapter(stationList, this)

        builder.setView(view)
        return builder.create()
    }

    override fun onStation(stationId: Long) {
        stationListener!!.onStation(stationId)
        dismiss()
    }

    companion object {

        val KEY_STATION_IDS = "KEY_STATION_IDS"
        private val TAG = AddStationDialog::class.java.simpleName

        fun show(supportFragmentManager: FragmentManager, stationIdsArray: LongArray) {
            val dialogFragment = AddStationDialog()
            val bundle = Bundle()
            bundle.putLongArray(KEY_STATION_IDS, stationIdsArray)
            dialogFragment.arguments = bundle
            dialogFragment.show(supportFragmentManager, TAG)
        }
    }
}
