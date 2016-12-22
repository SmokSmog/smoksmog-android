package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import java.util.List;

import pl.malopolska.smoksmog.model.Station;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class StationDialogAdapter extends RecyclerView.Adapter<StationViewHolder> {

    private final List<Station> stationList;
    private final StationListener stationListener;

    public StationDialogAdapter(List<Station> stationList, StationListener stationListener) {
        this.stationList = stationList;
        this.stationListener = stationListener;
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        int padding = parent.getResources().getDimensionPixelSize(R.dimen.margin_16);
        textView.setPadding(padding, padding, padding, padding);
        return new StationViewHolder(textView, new StationViewHolder.StationClickListener() {
            @Override
            public void onClick(long stationId) {
                stationListener.onStation(stationId);
            }
        });
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, int position) {
        holder.bind(stationList.get(position));
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public interface StationListener {

        void onStation(long stationId);
    }
}
