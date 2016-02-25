package com.antyzero.smoksmog.ui.screen.start.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ListViewHolder<T> extends RecyclerView.ViewHolder {

    public ListViewHolder( View itemView ) {
        super( itemView );
        itemView.setAlpha( 0f );
    }

    public void bind( T data ) {
        itemView.animate().alpha( 1f )
                .setStartDelay( 100L * getLayoutPosition() )
                .setDuration( 200L ).start();
    }
}
