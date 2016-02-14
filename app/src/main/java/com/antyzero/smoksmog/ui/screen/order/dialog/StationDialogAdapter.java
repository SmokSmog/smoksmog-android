package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.malopolska.smoksmog.model.Station;


public class StationDialogAdapter extends RecyclerView.Adapter<StationViewHolder> {

    private final List<Station> stationList;

    public StationDialogAdapter( List<Station> stationList ) {
        this.stationList = stationList;
    }

    @Override
    public StationViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        return new StationViewHolder( new TextView(parent.getContext()) );
    }

    @Override
    public void onBindViewHolder( StationViewHolder holder, int position ) {
        holder.bind( stationList.get( position ) );
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
