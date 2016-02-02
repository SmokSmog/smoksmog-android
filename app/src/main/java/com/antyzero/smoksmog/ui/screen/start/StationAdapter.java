package com.antyzero.smoksmog.ui.screen.start;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Set;

import pl.malopolska.smoksmog.model.Station;

/**
 *
 */
public class StationAdapter extends RecyclerView.Adapter {

    private final Set<Station> station;

    public StationAdapter( Set<Station> station ) {
        this.station = station;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        return null;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
