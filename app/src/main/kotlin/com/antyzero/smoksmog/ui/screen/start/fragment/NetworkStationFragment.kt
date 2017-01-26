package com.antyzero.smoksmog.ui.screen.start.fragment

import android.os.Bundle
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.FragmentModule
import com.trello.rxlifecycle.android.FragmentEvent
import pl.malopolska.smoksmog.model.Station
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NetworkStationFragment : StationFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = activity

        SmokSmogApplication[activity].appComponent
                .plus(ActivityModule(activity))
                .plus(FragmentModule(this))
                .inject(this)

        loadData()
    }

    override fun loadData() {
        api.station(stationId)
                .doOnSubscribe { showLoading() }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent<Any>(FragmentEvent.DESTROY_VIEW))
                .cast(Station::class.java)
                .subscribe(
                        { station -> updateUI(station) }
                ) { throwable ->
                    try {
                        showTryAgain(R.string.error_unable_to_load_station_data)
                    } catch (e: Exception) {
                        logger.e(TAG, "Problem with error handling code", e)
                    } finally {
                        logger.i(TAG, "Unable to load station data (stationID:$stationId)", throwable)
                    }
                }
    }

    companion object {

        private val TAG = NetworkStationFragment::class.java.simpleName
    }
}
