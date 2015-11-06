package com.antyzero.smoksmog.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import java.util.List;

import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;


public class ParticulateAdapter extends RecyclerView.Adapter<ParticulateAdapter.ParticulateViewHolder> {

    private final List<Particulate> particulateList;

    public ParticulateAdapter( List<Particulate> particulateList ) {
        if( particulateList == null){
            throw new IllegalArgumentException( "particulateList cannot be null" );
        }
        this.particulateList = particulateList;
    }

    @Override
    public ParticulateViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        return new ParticulateViewHolder( LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_particulate, parent, false ) );
    }

    @Override
    public void onBindViewHolder( ParticulateViewHolder holder, int position ) {

    }

    @Override
    public int getItemCount() {
        return particulateList.size();
    }

    public static class ParticulateViewHolder extends RecyclerView.ViewHolder {

        public ParticulateViewHolder( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
