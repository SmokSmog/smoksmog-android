package com.antyzero.smoksmog.ui.screen.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Station;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {

    public OrderItemViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
    }

    public void bind( Station station ){

    }
}
