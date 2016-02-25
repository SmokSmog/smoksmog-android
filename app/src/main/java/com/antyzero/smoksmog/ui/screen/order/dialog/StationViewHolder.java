package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import pl.malopolska.smoksmog.model.Station;

public class StationViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;
    private final StationClickListener stationClickListener;

    public StationViewHolder( TextView textView, StationClickListener stationClickListener ) {
        super( textView );
        this.textView = textView;
        this.stationClickListener = stationClickListener;
    }

    public void bind( Station station ) {
        textView.setText( station.getName() );
        itemView.setOnClickListener( v -> stationClickListener.onClick( station.getId() ) );
    }

    public interface StationClickListener {

        void onClick( long stationId );
    }
}
