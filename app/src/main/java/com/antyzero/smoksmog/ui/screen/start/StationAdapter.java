package com.antyzero.smoksmog.ui.screen.start;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.screen.start.item.AirQuality;
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewHolder;

import java.util.List;

import pl.malopolska.smoksmog.model.Station;

/**
 *
 */
public class StationAdapter extends RecyclerView.Adapter {

    private static final int TYPE_AIR_QUALITY = Integer.MIN_VALUE;

    private final List<Station> stationContainer;

    public StationAdapter( List<Station> stationContainer ) {
        this.stationContainer = stationContainer;
        setHasStableIds( true );
    }

    @Override
    public int getItemViewType( int position ) {
        return position == 0 ? TYPE_AIR_QUALITY : super.getItemViewType( position );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );

        switch ( viewType ) {

            case TYPE_AIR_QUALITY:
                return new AirQuality( layoutInflater.inflate( R.layout.item_air_quility, parent, false ) );

            default:
                return new ParticulateViewHolder( layoutInflater.inflate( R.layout.item_particulate, parent, false ) );
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {


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
