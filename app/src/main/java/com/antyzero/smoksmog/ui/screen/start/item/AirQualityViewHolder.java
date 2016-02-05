package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.air.AirQuality;
import com.antyzero.smoksmog.air.AirQualityIndex;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;

import static android.view.View.VISIBLE;

public class AirQualityViewHolder extends ListViewHolder<List<Particulate>> {

    @Bind( R.id.textViewIndexValue )
    TextView textViewIndexValue;
    @Bind( R.id.textViewAirQuality )
    TextView textViewAirQuality;
    @Bind( R.id.airIndicator )
    ImageView airIndicator;

    public AirQualityViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
    }

    @Override
    public void bind( List<Particulate> data ) {

        double indexValue = AirQualityIndex.calculate( data );
        AirQuality airQuality = AirQuality.findByValue( indexValue );

        textViewIndexValue.setText( String.format( Locale.getDefault(), "%.1f", indexValue ) );
        textViewAirQuality.setText( airQuality.getTitleResId() );
        airIndicator.setColorFilter( airQuality.getColor( itemView.getContext() ) );
        airIndicator.setVisibility( VISIBLE );
    }
}
