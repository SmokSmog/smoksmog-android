package com.antyzero.smoksmog.ui.screen.start.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ListViewHolder<T> extends RecyclerView.ViewHolder {

    public ListViewHolder( View itemView ) {
        super( itemView );
    }

    public abstract void bind( T data );
}
