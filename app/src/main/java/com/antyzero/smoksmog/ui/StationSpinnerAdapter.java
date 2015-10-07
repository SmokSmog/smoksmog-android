package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Station;


public class StationSpinnerAdapter extends ArrayAdapter<Station> {

    private static final int RESOURCE = android.R.layout.simple_spinner_item;

    private final LayoutInflater layoutInflater;

    public StationSpinnerAdapter( Context context, List<Station> objects ) {
        super( context, RESOURCE, objects );
        setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        layoutInflater = LayoutInflater.from( context );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder;
        
        if ( convertView != null ) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate( RESOURCE, parent, false );
            holder = new ViewHolder( convertView );
            convertView.setTag( holder );
        }

        holder.textView.setText( getItem( position ).getName() );

        return convertView;
    }

    static class ViewHolder {

        @Bind( android.R.id.text1 )
        TextView textView;

        public ViewHolder( View view ) {
            ButterKnife.bind( this, view );
        }
    }
}
