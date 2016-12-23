package com.antyzero.smoksmog.ui.screen.start.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.eventbus.RxBus
import com.antyzero.smoksmog.fabric.StationShowEvent
import com.antyzero.smoksmog.ui.BaseFragment
import com.antyzero.smoksmog.ui.screen.start.StartActivity
import com.antyzero.smoksmog.ui.screen.start.StationAdapter
import com.antyzero.smoksmog.ui.screen.start.TitleProvider
import com.crashlytics.android.answers.Answers
import com.trello.rxlifecycle.FragmentEvent
import kotlinx.android.synthetic.main.fragment_station.*
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import smoksmog.logger.Logger
import java.util.*
import javax.inject.Inject


abstract class StationFragment : BaseFragment(), TitleProvider {

    @Inject
    lateinit var rxBus: RxBus
    @Inject
    lateinit protected var smokSmog: SmokSmog
    @Inject
    lateinit protected var logger: Logger
    @Inject
    lateinit protected var errorReporter: ErrorReporter
    @Inject
    lateinit var answers: Answers

    private val stationContainer = ArrayList<Station>()
    /**
     * Access fragment station

     * @return Station instance or null if station is not yet loaded
     */
    var station: Station? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!arguments.containsKey(ARG_STATION_ID)) {
            throw IllegalStateException(String.format(
                    "%s should be created with newInstance method, missing ARG_STATION_ID",
                    StationFragment::class.java.simpleName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
        recyclerView.adapter = StationAdapter(stationContainer)

        progressBar.indeterminateDrawable.setColorFilter(
                resources.getColor(R.color.accent),
                PorterDuff.Mode.SRC_IN)

        showLoading()
    }

    /**
     * Implement for data load
     */
    protected abstract fun loadData()

    override val title: String
        get() = if (station == null) "" else station!!.name

    override val subtitle: String
        get() = if (stationId == NEAREST_STATION_ID.toLong()) getString(R.string.station_closest) else ""

    protected fun showLoading() = updateViewsOnUiThread(Runnable { viewAnimator.displayedChild = 1 })

    protected fun showData() = updateViewsOnUiThread(Runnable { viewAnimator.displayedChild = 0 })

    protected fun showTryAgain(@StringRes errorReport: Int) = updateViewsOnUiThread(Runnable {
        viewAnimator.displayedChild = 2
        textViewError.visibility = View.VISIBLE
        textViewError.text = getString(errorReport)
    })

    /**
     * Use to update views on main thread, in case fragment is not accessible (post view destroy)
     * ignore this call.

     * @param givenRunnable for view update
     */
    protected fun updateViewsOnUiThread(givenRunnable: Runnable) {
        Observable.just(givenRunnable)
                .compose(bindUntilEvent<Any>(FragmentEvent.DESTROY_VIEW))
                .filter { !isDetached }
                .cast(Runnable::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Runnable::run) { logger.e(TAG, "Unable to update views") }
    }

    @OnClick(R.id.buttonTryAgain)
    internal fun buttonReloadData() {
        textViewError.postDelayed({ textViewError.visibility = View.GONE }, 1000L)
        loadData()
    }

    /**
     * Update UI with new station data

     * @param station data
     */
    protected fun updateUI(station: Station) {
        this.station = station

        stationContainer.clear()
        stationContainer.add(station)
        recyclerView.adapter.notifyDataSetChanged()

        rxBus.send(StartActivity.TitleUpdateEvent())

        answers.logContentView(StationShowEvent(station))

        showData()
    }

    val stationId: Long
        get() = arguments.getLong(ARG_STATION_ID)

    companion object {

        private val TAG = StationFragment::class.java.simpleName
        private val ARG_STATION_ID = "argStationId"
        private val NEAREST_STATION_ID = 0

        fun newInstance(stationId: Long): StationFragment {
            return (if (stationId <= 0) LocationStationFragment() else NetworkStationFragment()).apply {
                this.arguments = Bundle().apply {
                    this.putLong(ARG_STATION_ID, stationId)
                }
            }
        }
    }
}
