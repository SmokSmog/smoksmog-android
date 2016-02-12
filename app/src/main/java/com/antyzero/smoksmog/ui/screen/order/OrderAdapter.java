package com.antyzero.smoksmog.ui.screen.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import java.util.Collections;
import java.util.List;

import pl.malopolska.smoksmog.model.Station;

public class OrderAdapter extends RecyclerView.Adapter<OrderItemViewHolder> implements ItemTouchHelperAdapter {

    private final List<Station> stationList;

    public OrderAdapter( List<Station> stationList ) {
        this.stationList = stationList;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        return new OrderItemViewHolder( LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_order, parent, false ) );
    }

    @Override
    public void onBindViewHolder( OrderItemViewHolder holder, int position ) {
        holder.bind( stationList.get( position ) );
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    @Override
    public void onItemMove( int fromPosition, int toPosition ) {
        if ( fromPosition < toPosition ) {
            for ( int i = fromPosition; i < toPosition; i++ ) {
                Collections.swap( stationList, i, i + 1 );
            }
        } else {
            for ( int i = fromPosition; i > toPosition; i-- ) {
                Collections.swap( stationList, i, i - 1 );
            }
        }
        notifyItemMoved( fromPosition, toPosition );
    }

    @Override
    public void onItemDismiss( int position ) {
        stationList.remove( position );
        notifyItemRemoved( position );
    }
}
