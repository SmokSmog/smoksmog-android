package com.antyzero.smoksmog.ui.screen.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Station;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {

    @Bind( R.id.textView )
    TextView textView;
    @Bind( R.id.viewHandle )
    View viewHandle;

    public OrderItemViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
    }

    public View getHandleView(){
        return viewHandle;
    }

    public void bind( Station station ){
        textView.setText( station.getName() );
    }
}
