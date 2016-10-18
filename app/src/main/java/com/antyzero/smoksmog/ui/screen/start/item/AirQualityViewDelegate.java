package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;

import pl.malopolska.smoksmog.model.Station;

public class AirQualityViewDelegate extends ViewDelegate<AirQualityViewHolder, Station> {

    public AirQualityViewDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public AirQualityViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new AirQualityViewHolder(layoutInflater.inflate(R.layout.item_air_quility, parent, false));
    }
}
