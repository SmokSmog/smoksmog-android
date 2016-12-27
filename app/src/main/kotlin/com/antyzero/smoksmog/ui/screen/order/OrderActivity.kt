package com.antyzero.smoksmog.ui.screen.order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout.LayoutParams
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.navBarHeight
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.PickStationActivity
import com.antyzero.smoksmog.ui.screen.order.dialog.StationDialogAdapter
import com.antyzero.smoksmog.ui.statusBarHeight
import kotlinx.android.synthetic.main.activity_order.*
import pl.malopolska.smoksmog.SmokSmog
import pl.malopolska.smoksmog.model.Station
import pl.malopolska.smoksmog.utils.StationUtils
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import smoksmog.logger.Logger
import javax.inject.Inject

class OrderActivity : BaseDragonActivity(), OnStartDragListener, StationDialogAdapter.StationListener {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var settingsHelper: SettingsHelper
    @Inject lateinit var logger: Logger
    @Inject lateinit var errorReporter: ErrorReporter

    lateinit private var itemTouchHelper: ItemTouchHelper
    private val stationList: MutableList<Station> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)
        container.setPadding(0, statusBarHeight(), 0, 0)

        if (intent != null && intent.getBooleanExtra(EXTRA_DIALOG, false)) {
            startStationPick()
        }

        setupFAB()
        setupNavigationBar()

        recyclerView.setPadding(
                0, 0,
                0, resources.getDimensionPixelSize(R.dimen.item_air_quality_height) * 3)

        SmokSmogApplication[this]
                .appComponent
                .plus(ActivityModule(this))
                .inject(this)

        val adapter = OrderAdapter(stationList, this, settingsHelper)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)

        val callback = SimpleItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val stationIds = settingsHelper.stationIdList

        smokSmog.api.stations()
                .subscribeOn(Schedulers.newThread())
                .flatMap { stations -> Observable.from(stations) }
                .filter { station -> stationIds.contains(station.id) }
                .toSortedList { station, station2 -> stationIds.indexOf(station.id) - stationIds.indexOf(station2.id) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stations ->
                            stationList.clear()
                            stationList.addAll(stations)
                            recyclerView.adapter.notifyDataSetChanged()
                        }
                ) { throwable -> logger.w(TAG, "Unable to build list", throwable) }

    }

    private fun setupFAB() {
        val margin = resources.getDimensionPixelSize(R.dimen.margin_16)
        val params = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        var bottomMargin = margin

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomMargin += navBarHeight()
        }

        params.bottomMargin = bottomMargin
        params.leftMargin = margin
        params.rightMargin = margin
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.anchorGravity = Gravity.BOTTOM or Gravity.END
        params.anchorId = R.id.recyclerView

        fab.layoutParams = params
        fab.setOnClickListener { startStationPick() }
    }

    private fun setupNavigationBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    private fun startStationPick() {
        val list = Observable.from(stationList)
                .map(Station::id)
                .toList()
                .toBlocking().first()
        val ids = convertListToArray(list)
        PickStationActivity.startForResult(this, PICK_STATION_REQUEST, ids)
    }

    private fun convertListToArray(list: List<Long>): IntArray {
        val array = IntArray(list.size)
        for (i in list.indices) {
            array[i] = list[i].toInt()
        }
        return array
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_STATION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                onStation(PickStationActivity.gatherResult(data).first)
            } else {
                Toast.makeText(this, "Nie wybrano stacji", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onStation(stationId: Long) {
        smokSmog.api.station(stationId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { station ->
                            stationList.add(station)
                            recyclerView.adapter.notifyDataSetChanged()
                            settingsHelper.stationIdList.let {
                                it.clear()
                                it.addAll(StationUtils.Companion.convertStationsToIdsList(stationList))
                            }
                        }
                ) { throwable ->
                    logger.e(TAG, "Unable to add station to station list", throwable)
                    errorReporter.report(R.string.error_unable_to_add_station)
                }
    }

    companion object {

        private val TAG = OrderActivity::class.java.simpleName
        private val EXTRA_DIALOG = "EXTRA_DIALOG"
        private val PICK_STATION_REQUEST = 8925

        @JvmOverloads fun start(context: Context, stationPickUp: Boolean = false) {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra(EXTRA_DIALOG, stationPickUp)
            context.startActivity(intent)
        }
    }
}
