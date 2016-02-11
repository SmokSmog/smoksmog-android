package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateViewDelegate  extends ViewDelegate<ParticulateViewHolder, Particulate>{

    public ParticulateViewDelegate( int viewType ) {
        super( viewType );
    }

    @Override
    public ParticulateViewHolder onCreateViewHolder( ViewGroup parent ) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        return new ParticulateViewHolder( inflater.inflate( R.layout.item_particulate, parent, false ) );
    }
}
