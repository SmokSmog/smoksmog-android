package com.antyzero.smoksmog.ui.screen.start.fragment

import android.location.Location
import android.os.Bundle
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.google.GoogleModule
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.FragmentModule
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.trello.rxlifecycle.android.FragmentEvent
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationStationFragment : StationFragment(), GoogleApiClient.ConnectionCallbacks {
    @Inject
    lateinit var googleApiClient: GoogleApiClient
    private var locationCurrent: Location? = null
    private var locationStation: Location? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = activity
        SmokSmogApplication[activity].appComponent
                .plus(ActivityModule(activity))
                .plus(FragmentModule(this), GoogleModule(this))
                .inject(this)

        googleApiClient.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        loadData()
    }

    override fun loadData() {
        val reactiveLocationProvider = ReactiveLocationProvider(activity)

        val request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setNumUpdates(1)
                .setExpirationDuration(TimeUnit.SECONDS.toMillis(LOCATION_TIMEOUT_IN_SECONDS))

        reactiveLocationProvider.getUpdatedLocation(request)
                .compose(bindUntilEvent<Any>(FragmentEvent.DESTROY_VIEW))
                .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .first()
                .cast(Location::class.java)
                .doOnNext { location -> this@LocationStationFragment.locationCurrent = location }
                .flatMap { location -> api.stationByLocation(location.latitude, location.longitude) }
                .doOnNext { givenStation ->
                    api.stations()
                            .concatMap { stations -> Observable.from(stations) }
                            .filter { station -> station.id == givenStation.id }
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(bindUntilEvent<Any>(FragmentEvent.DESTROY))
                            .cast(Station::class.java)
                            .subscribe { station1 ->
                                locationStation = Location(locationCurrent)
                                locationStation!!.longitude = station1.longitude.toDouble()
                                locationStation!!.latitude = station1.latitude.toDouble()
                            }
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    this@LocationStationFragment.locationCurrent = null
                    showLoading()
                }
                .cast(Station::class.java)
                .subscribe(
                        { station -> updateViewsOnUiThread(Runnable { updateUI(station) }) }
                ) { throwable ->
                    try {
                        showTryAgain(R.string.error_no_near_Station)
                    } catch (e: Exception) {
                        logger.e(TAG, "Problem with error handling code", e)
                    } finally {
                        logger.i(TAG, "Unable to find closes station", throwable)
                    }
                }
    }

    override fun onConnectionSuspended(i: Int) {
        // do nothing
    }

    companion object {

        private val TAG = LocationStationFragment::class.java.simpleName

        private val LOCATION_TIMEOUT_IN_SECONDS: Long = 4
    }
}
