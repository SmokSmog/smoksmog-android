package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.ViewGroup;

public abstract class ViewDelegate<T extends ListViewHolder<R>, R> {

    private final int viewType;

    public ViewDelegate( int viewType ) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public abstract T onCreateViewHolder( ViewGroup parent );

    public void onBindViewHolder( T holder, R data ) {
        holder.bind( data );
    }
}
