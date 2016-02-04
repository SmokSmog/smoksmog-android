package com.antyzero.smoksmog.ui.screen.start.item;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class ViewDelegate<T extends RecyclerView.ViewHolder, R> {

    private final int viewType;

    public ViewDelegate( int viewType ) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public abstract T onCreateViewHolder( ViewGroup parent );

    public abstract void onBindViewHolder( T holder, R data );
}
