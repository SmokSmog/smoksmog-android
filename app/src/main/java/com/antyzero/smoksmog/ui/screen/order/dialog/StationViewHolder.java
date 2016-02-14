package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import pl.malopolska.smoksmog.model.Station;

public class StationViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public StationViewHolder( TextView textView ) {
        super( textView );
        this.textView = textView;
    }

    public void bind( Station station ){
        textView.setText( station.getName() );
    }
}
