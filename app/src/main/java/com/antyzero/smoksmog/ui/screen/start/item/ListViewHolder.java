package com.antyzero.smoksmog.ui.screen.start.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ListViewHolder<T> extends RecyclerView.ViewHolder {

    private Context context;

    public ListViewHolder( View itemView ) {
        super( itemView );
        this.context = itemView.getContext();
    }

    public Context getContext() {
        return context;
    }

    public void bind( T data ) {
    }
}
