package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.air.AirQuality;
import com.antyzero.smoksmog.air.AirQualityIndex;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.ui.dialog.InfoDialog;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.malopolska.smoksmog.model.Particulate;

import static android.view.View.VISIBLE;

public class AirQualityViewHolder extends ListViewHolder<List<Particulate>> {

    @Inject
    RxBus rxBus;

    @Bind( R.id.textViewIndexValue )
    TextView textViewIndexValue;
    @Bind( R.id.textViewAirQuality )
    TextView textViewAirQuality;
    @Bind( R.id.airIndicator )
    ImageView airIndicator;
    @Bind( R.id.buttonAirQualityInfo )
    View buttonAirQualityInfo;

    public AirQualityViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );

        SmokSmogApplication.get( itemView.getContext() ).getAppComponent().inject( this );
    }

    @Override
    public void bind( List<Particulate> data ) {

        double indexValue = AirQualityIndex.calculate( data );
        AirQuality airQuality = AirQuality.findByValue( indexValue );

        textViewIndexValue.setText( String.format( Locale.getDefault(), "%.1f", indexValue ) );
        textViewAirQuality.setText( airQuality.getTitleResId() );
        airIndicator.setVisibility( VISIBLE );
        airIndicator.setColorFilter( airQuality.getColor( itemView.getContext() ) );
    }

    @OnClick( R.id.buttonAirQualityInfo )
    void clickInfo(){
        rxBus.send( new InfoDialog.Event( R.layout.info_air_quality ) );
    }
}
