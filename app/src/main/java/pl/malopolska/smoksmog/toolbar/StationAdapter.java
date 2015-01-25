package pl.malopolska.smoksmog.toolbar;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import pl.malopolska.smoksmog.network.StationLocation;

public class StationAdapter extends BaseAdapter {

    private final List<StationLocation> stationList;

    public StationAdapter(@NonNull List<StationLocation> stationList) {
        this.stationList = stationList;
    }

    @Override
    public int getCount() {
        return stationList.size();
    }

    @Override
    public StationLocation getItem(int position) {
        return stationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
