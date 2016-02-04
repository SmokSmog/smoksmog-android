package com.antyzero.smoksmog.ui.screen.start;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewDelegate;
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewHolder;
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewDelegate;
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewHolder;

import java.util.List;

import pl.malopolska.smoksmog.model.Station;

/**
 *
 */
public class StationAdapter extends RecyclerView.Adapter {

    private static final int TYPE_AIR_QUALITY = Integer.MIN_VALUE;
    private static final int TYPE_PARTICULATE = 0;

    private final List<Station> stationContainer;

    private final AirQualityViewDelegate airQualityViewDelegate;
    private final ParticulateViewDelegate particulateViewDelegate;

    public StationAdapter( List<Station> stationContainer ) {
        this.stationContainer = stationContainer;
        setHasStableIds( true );

        airQualityViewDelegate = new AirQualityViewDelegate( TYPE_AIR_QUALITY );
        particulateViewDelegate = new ParticulateViewDelegate( TYPE_PARTICULATE );
    }

    @Override
    public int getItemViewType( int position ) {
        return position == 0 ? TYPE_AIR_QUALITY : TYPE_PARTICULATE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {


    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {

        Station station = stationContainer.get( 0 );

        if ( station == null ) {
            return;
        }

        if ( position == 0 ) {
            airQualityViewDelegate.onBindViewHolder( (AirQualityViewHolder) holder, station.getParticulates() );
        } else {

        }

    }

    @Override
    public int getItemCount() {
        int particulates = 0;

        if ( !stationContainer.isEmpty() ) {
            Station station = this.stationContainer.get( 0 );
            if ( station != null ) {
                particulates = station.getParticulates().size();
            }
        }

        return 1 + particulates;
    }
}
