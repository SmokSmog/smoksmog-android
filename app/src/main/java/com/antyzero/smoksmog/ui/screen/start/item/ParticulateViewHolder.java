package com.antyzero.smoksmog.ui.screen.start.item;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.view.IndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateViewHolder extends ListViewHolder<Particulate> {

    private final Resources resources;

    @Bind( R.id.textViewName )
    TextView textViewName;
    @Bind( R.id.textViewMeasureDay )
    TextView textViewMeasureDay;
    @Bind( R.id.textViewMeasureHour )
    TextView textViewMeasureHour;
    @Bind( R.id.indicatorView )
    IndicatorView indicatorView;

    public ParticulateViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
        resources = itemView.getContext().getResources();
    }

    @Override
    public void bind( Particulate data ) {
        textViewName.setText( data.getShortName() );
        textViewMeasureDay.setText( resources.getString( R.string.measurment, data.getAverage(), data.getUnit() ) );
        textViewMeasureHour.setText( resources.getString( R.string.measurment, data.getValue(), data.getUnit() ) );
        indicatorView.setValue( data.getAverage() / data.getNorm() );
    }
}
