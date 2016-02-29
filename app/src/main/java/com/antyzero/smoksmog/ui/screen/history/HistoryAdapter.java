package com.antyzero.smoksmog.ui.screen.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import java.util.List;

import pl.malopolska.smoksmog.model.Particulate;


public class HistoryAdapter extends RecyclerView.Adapter<ParticulateHistoryViewHolder> {

    private List<Particulate> particulates;

    public HistoryAdapter( List<Particulate> particulates ) {
        this.particulates = particulates;
    }

    @Override
    public ParticulateHistoryViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        return new ParticulateHistoryViewHolder( inflater.inflate( R.layout.item_chart, parent, false ) );
    }

    @Override
    public int getItemCount() {
        return particulates.size();
    }

    @Override
    public void onBindViewHolder( ParticulateHistoryViewHolder holder, int position ) {
        holder.bind( particulates.get( position ) );
    }

}