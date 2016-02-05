package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import java.util.List;

import pl.malopolska.smoksmog.model.Particulate;

public class AirQualityViewDelegate extends ViewDelegate<AirQualityViewHolder, List<Particulate>> {

    public AirQualityViewDelegate( int viewType ) {
        super( viewType );
    }

    @Override
    public AirQualityViewHolder onCreateViewHolder( ViewGroup parent ){
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        return new AirQualityViewHolder( layoutInflater.inflate( R.layout.item_air_quility, parent, false ) );
    }

    @Override
    public void onBindViewHolder( AirQualityViewHolder holder, List<Particulate> data ) {
        holder.bind( data );
    }
}
