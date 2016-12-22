package com.antyzero.smoksmog.ui.screen.start.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.eventbus.RxBus
import com.antyzero.smoksmog.time.CountdownProvider
import com.antyzero.smoksmog.ui.dialog.AirQualityDialog
import com.antyzero.smoksmog.ui.dialog.InfoDialog
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity
import com.trello.rxlifecycle.RxLifecycle

import org.joda.time.DateTime
import org.joda.time.Seconds

import java.util.Comparator
import java.util.Locale
import java.util.concurrent.TimeUnit

import javax.inject.Inject

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import pl.malopolska.smoksmog.model.Particulate
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.math.operators.OperatorMinMax
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex
import smoksmog.logger.Logger

import android.view.View.VISIBLE

class AirQualityViewHolder(itemView: View) : ListViewHolder<Station>(itemView) {

    @Inject
    lateinit var rxBus: RxBus
    @Inject
    lateinit var countdownProvider: CountdownProvider
    @Inject
    lateinit var logger: Logger

    @Bind(R.id.textViewIndexValue)
    internal var textViewIndexValue: TextView? = null
    @Bind(R.id.textViewAirQuality)
    internal var textViewAirQuality: TextView? = null
    @Bind(R.id.textViewMeasureTime)
    internal var textViewMeasureTime: TextView? = null
    @Bind(R.id.airIndicator)
    internal var airIndicator: ImageView? = null
    @Bind(R.id.buttonAirQualityInfo)
    internal var buttonAirQualityInfo: View? = null
    @Bind(R.id.buttonTimeline)
    internal var buttonTimeline: View? = null

    private var measureDate: DateTime? = null

    init {
        ButterKnife.bind(this, itemView)
        SmokSmogApplication[itemView.context].appComponent.inject(this)
    }

    override fun bind(station: Station) {
        super.bind(station)

        buttonTimeline!!.setOnClickListener { HistoryActivity.start(context, station.id) }
        buttonTimeline!!.visibility = VISIBLE

        val indexValue = AirQualityIndex.calculate(station)
        val airQuality = AirQuality.findByValue(indexValue)

        textViewIndexValue!!.text = String.format(Locale.getDefault(), "%.1f", indexValue)
        textViewAirQuality!!.setText(airQuality.titleResId)
        airIndicator!!.visibility = VISIBLE
        airIndicator!!.setColorFilter(airQuality.getColor(itemView.context))

        val particulates = station.particulates

        if (!particulates!!.isEmpty()) {

            val particulate = getNewest(particulates)
            measureDate = particulate.date
            updateUiTime()

            // Loop update, we want up to date info
            Observable.interval(30, TimeUnit.SECONDS)
                    .compose(RxLifecycle.bindView<Any>(textViewMeasureTime!!))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ updateUiTime() }
                    ) { throwable -> logger!!.i(TAG, "Unable to refresh time", throwable) }
        }
    }

    /**
     * Updates info when last measurement occurred
     */
    private fun updateUiTime() {
        if (measureDate != null) {
            val seconds = Seconds.secondsBetween(measureDate!!, DateTime.now()).seconds
            textViewMeasureTime!!.text = String.format(
                    itemView.resources.getText(R.string.measure_ago).toString(),
                    countdownProvider!![seconds])
            textViewMeasureTime!!.setBackgroundColor(itemView.resources.getColor(
                    if (seconds >= 4 * 60 * 60) R.color.red else android.R.color.transparent))
        }
    }

    private fun getNewest(data: List<Particulate>): Particulate {
        return OperatorMinMax.max(Observable.from(data)) { lhs, rhs -> lhs.date.compareTo(rhs.date) }.toBlocking().first()
    }

    @OnClick(R.id.buttonAirQualityInfo)
    internal fun clickInfo() {
        rxBus!!.send(InfoDialog.Event(AirQualityDialog::class.java))
    }

    companion object {

        private val TAG = AirQualityViewHolder::class.java.simpleName
    }
}
