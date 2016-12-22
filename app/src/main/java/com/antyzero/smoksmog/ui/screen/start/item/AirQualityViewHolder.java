package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.time.CountdownProvider;
import com.antyzero.smoksmog.ui.dialog.AirQualityDialog;
import com.antyzero.smoksmog.ui.dialog.InfoDialog;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;
import com.trello.rxlifecycle.RxLifecycle;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.math.operators.OperatorMinMax;
import smoksmog.air.AirQuality;
import smoksmog.air.AirQualityIndex;
import smoksmog.logger.Logger;

import static android.view.View.VISIBLE;

public class AirQualityViewHolder extends ListViewHolder<Station> {

    private static final String TAG = AirQualityViewHolder.class.getSimpleName();

    @Inject
    RxBus rxBus;
    @Inject
    CountdownProvider countdownProvider;
    @Inject
    Logger logger;

    @Bind(R.id.textViewIndexValue)
    TextView textViewIndexValue;
    @Bind(R.id.textViewAirQuality)
    TextView textViewAirQuality;
    @Bind(R.id.textViewMeasureTime)
    TextView textViewMeasureTime;
    @Bind(R.id.airIndicator)
    ImageView airIndicator;
    @Bind(R.id.buttonAirQualityInfo)
    View buttonAirQualityInfo;
    @Bind(R.id.buttonTimeline)
    View buttonTimeline;

    private DateTime measureDate = null;

    public AirQualityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SmokSmogApplication.Companion.get(itemView.getContext()).getAppComponent().inject(this);
    }

    @Override
    public void bind(final Station station) {
        super.bind(station);

        buttonTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.start(getContext(), station.getId());
            }
        });
        buttonTimeline.setVisibility(VISIBLE);

        double indexValue = AirQualityIndex.calculate(station);
        AirQuality airQuality = AirQuality.findByValue(indexValue);

        textViewIndexValue.setText(String.format(Locale.getDefault(), "%.1f", indexValue));
        textViewAirQuality.setText(airQuality.getTitleResId());
        airIndicator.setVisibility(VISIBLE);
        airIndicator.setColorFilter(airQuality.getColor(itemView.getContext()));

        List<Particulate> particulates = station.getParticulates();

        if (!particulates.isEmpty()) {

            Particulate particulate = getNewest(particulates);
            measureDate = particulate.getDate();
            updateUiTime();

            // Loop update, we want up to date info
            Observable.interval(30, TimeUnit.SECONDS)
                    .compose(RxLifecycle.bindView(textViewMeasureTime))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                                   @Override
                                   public void call(Object o) {
                                       updateUiTime();
                                   }
                               },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    logger.i(TAG, "Unable to refresh time", throwable);
                                }
                            });
        }
    }

    /**
     * Updates info when last measurement occurred
     */
    private void updateUiTime() {
        if (measureDate != null) {
            int seconds = Seconds.secondsBetween(measureDate, DateTime.now()).getSeconds();
            textViewMeasureTime.setText(String.format(
                    itemView.getResources().getText(R.string.measure_ago).toString(),
                    countdownProvider.get(seconds)));
            textViewMeasureTime.setBackgroundColor(itemView.getResources().getColor(
                    seconds >= 4 * 60 * 60 ? R.color.red : android.R.color.transparent));
        }
    }

    private Particulate getNewest(List<Particulate> data) {
        return OperatorMinMax.max(Observable.from(data), new Comparator<Particulate>() {
            @Override
            public int compare(Particulate lhs, Particulate rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        }).toBlocking().first();
    }

    @OnClick(R.id.buttonAirQualityInfo)
    void clickInfo() {
        rxBus.send(new InfoDialog.Event<>(AirQualityDialog.class));
    }
}
