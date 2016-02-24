package com.antyzero.smoksmog.ui.screen.start.item;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.settings.Percent;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.view.IndicatorView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateViewHolder extends ListViewHolder<Particulate> {

    private final Resources resources;

    @Inject
    SettingsHelper settingsHelper;

    @Bind( R.id.textViewName )
    TextView textViewName;
    @Bind( R.id.textViewMeasureDay )
    TextView textViewMeasureDay;
    @Bind( R.id.textViewMeasureHour )
    TextView textViewMeasureHour;
    @Bind( R.id.textViewTimeHour )
    TextView textViewTimeHour;
    @Bind( R.id.textViewTimeDay )
    TextView textViewTimeDay;
    @Bind( R.id.indicatorView )
    IndicatorView indicatorView;

    public ParticulateViewHolder( View itemView ) {
        super( itemView );
        SmokSmogApplication.get( itemView.getContext() ).getAppComponent().inject( this );
        ButterKnife.bind( this, itemView );
        resources = itemView.getContext().getResources();
    }

    @Override
    public void bind( Particulate data ) {
        super.bind( data );

        textViewName.setText( data.getShortName() );
        textViewMeasureDay.setText( resources.getString( R.string.measurment, data.getAverage(), data.getUnit() ) );
        textViewMeasureHour.setText( resources.getString( R.string.measurment, data.getValue(), data.getUnit() ) );

        if ( data.getValue() > data.getNorm() ) {
            textViewTimeHour.setBackgroundResource( R.drawable.shape_oval_iron_border );
        } else {
            textViewTimeHour.setBackgroundResource( R.drawable.shape_oval_iron );
        }

        if ( data.getAverage() > data.getNorm() ) {
            textViewTimeDay.setBackgroundResource( R.drawable.shape_oval_iron_border );
        } else {
            textViewTimeDay.setBackgroundResource( R.drawable.shape_oval_iron );
        }

        if ( Percent.HOUR.equals( settingsHelper.getPercentMode() ) ) {
            indicatorView.setValue( data.getValue() / data.getNorm() );
        } else {
            indicatorView.setValue( data.getAverage() / data.getNorm() );
        }
    }
}
