package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateViewHolder extends ListViewHolder<Particulate> {

    @Bind( R.id.textViewName )
    TextView textViewName;

    public ParticulateViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
    }

    @Override
    public void bind( Particulate data ) {

        textViewName.setText( data.getName() );
    }
}
