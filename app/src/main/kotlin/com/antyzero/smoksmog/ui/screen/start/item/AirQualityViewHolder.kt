package com.antyzero.smoksmog.ui.screen.start.item

import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.findViewById
import com.antyzero.smoksmog.dsl.tag
import com.antyzero.smoksmog.eventbus.RxBus
import com.antyzero.smoksmog.time.CountdownProvider
import com.antyzero.smoksmog.ui.dialog.AirQualityDialog
import com.antyzero.smoksmog.ui.dialog.InfoDialog
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity
import com.trello.rxlifecycle.RxLifecycle
import org.joda.time.DateTime
import org.joda.time.Seconds
import pl.malopolska.smoksmog.model.Particulate
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.math.operators.OperatorMinMax
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex
import smoksmog.logger.Logger
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AirQualityViewHolder(itemView: View) : ListViewHolder<Station>(itemView) {

    @Inject lateinit var rxBus: RxBus
    @Inject lateinit var countdownProvider: CountdownProvider
    @Inject lateinit var logger: Logger

    var textViewIndexValue: TextView
    var textViewAirQuality: TextView
    var textViewMeasureTime: TextView
    var airIndicator: ImageView
    var buttonAirQualityInfo: View
    var buttonTimeline: View

    private var measureDate: DateTime? = null

    init {
        SmokSmogApplication[itemView.context].appComponent.inject(this)

        textViewIndexValue = findViewById(R.id.textViewIndexValue) as TextView
        textViewAirQuality = findViewById(R.id.textViewAirQuality) as TextView
        textViewMeasureTime = findViewById(R.id.textViewMeasureTime) as TextView
        airIndicator = findViewById(R.id.airIndicator) as ImageView
        buttonAirQualityInfo = findViewById(R.id.buttonAirQualityInfo)
        buttonTimeline = findViewById(R.id.buttonTimeline)
    }

    override fun bind(data: Station) {
        super.bind(data)

        buttonTimeline.setOnClickListener { HistoryActivity.start(context, data.id) }
        buttonTimeline.visibility = VISIBLE

        val indexValue = AirQualityIndex.calculate(data)
        val airQuality = AirQuality.Companion.findByValue(indexValue)

        textViewIndexValue.text = String.format(Locale.getDefault(), "%.1f", indexValue)
        textViewAirQuality.setText(airQuality.titleResId)
        airIndicator.visibility = VISIBLE
        airIndicator.setColorFilter(airQuality.getColor(itemView.context))

        val particulates = data.particulates
        if (!particulates.isEmpty()) {

            val particulate = getNewest(particulates)
            measureDate = particulate.date
            updateUiTime()

            // Loop update, we want up to date info
            Observable.interval(30, TimeUnit.SECONDS)
                    .compose(RxLifecycle.bindView<Any>(textViewMeasureTime))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ updateUiTime() }
                    ) { throwable -> logger.i(tag(), "Unable to refresh time", throwable) }
        }

        buttonAirQualityInfo.setOnClickListener {
            rxBus.send(InfoDialog.Event(AirQualityDialog::class.java))
        }
    }

    /**
     * Updates info when last measurement occurred
     */
    private fun updateUiTime() {
        if (measureDate != null) {
            val seconds = Seconds.secondsBetween(measureDate!!, DateTime.now()).seconds
            textViewMeasureTime.text = String.format(
                    itemView.resources.getText(R.string.measure_ago).toString(),
                    countdownProvider[seconds])
            textViewMeasureTime.setBackgroundColor(itemView.resources.getColor(
                    if (seconds >= 4 * 60 * 60) R.color.red else android.R.color.transparent))
        }
    }

    private fun getNewest(data: List<Particulate>): Particulate {
        return OperatorMinMax.max(Observable.from(data)) { lhs, rhs -> lhs.date.compareTo(rhs.date) }.toBlocking().first()
    }
}