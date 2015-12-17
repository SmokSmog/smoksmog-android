package com.antyzero.smoksmog.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;


public class ParticulateAdapter extends RecyclerView.Adapter<ParticulateAdapter.ParticulateViewHolder> {

    private final List<Particulate> particulateList;
    private final OnItemClickListener listener;

    public ParticulateAdapter( List<Particulate> particulateList, OnItemClickListener listener ) {

        if ( particulateList == null ) {
            throw new IllegalArgumentException( "particulateList cannot be null" );
        }
        if ( listener == null ) {
            throw new IllegalArgumentException( "Listener cannot be null" );
        }

        this.listener = listener;
        this.particulateList = particulateList;

        setHasStableIds( true );
    }

    @Override
    public ParticulateViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        return new ParticulateViewHolder( LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_particulate, parent, false ) );
    }

    @Override
    public void onBindViewHolder( ParticulateViewHolder holder, int position ) {
        holder.bind( particulateList.get( position ), listener );
    }

    @Override
    public long getItemId( int position ) {
        return particulateList.get( position ).getId();
    }

    @Override
    public int getItemCount() {
        return particulateList.size();
    }

    public static class ParticulateViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.textViewName )
        TextView textViewName;
        @Bind( R.id.textViewNorm )
        TextView textViewNorm;
        @Bind( R.id.indicatorView )
        IndicatorView indicatorView;

        public ParticulateViewHolder( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }

        public void bind( Particulate particulate, OnItemClickListener listener ) {
            indicatorView.setValue( particulate.getValue() / particulate.getNorm() );
            textViewName.setText( particulate.getShortName() );
            textViewNorm.setText( String.format( "%s%s", particulate.getNorm(), particulate.getUnit() ) );

            // Root view
            itemView.setOnClickListener( v -> listener.onItemClick( particulate ) );
        }
    }

    /**
     *
     */
    public interface OnItemClickListener {

        void onItemClick( Particulate particulate );
    }
}
