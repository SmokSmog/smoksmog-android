package pl.malopolska.smoksmog.toolbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.network.model.StationLocation;

public class StationAdapter extends BaseAdapter {

    private final List<StationLocation> stationList;
    private final LayoutInflater layoutInflater;

    public StationAdapter(Context context, @NonNull List<StationLocation> stationList) {
        this.stationList = stationList;
        this.layoutInflater = LayoutInflater.from(context);
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

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StationLocation stationLocation = getItem(position);

        viewHolder.textView.setText(stationLocation.getName());

        return convertView;
    }

    static final class ViewHolder {

        @InjectView(android.R.id.text1)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.inject(this,view);
        }
    }
}
