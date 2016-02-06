package com.antyzero.smoksmog.ui.screen.start.item;

import android.view.View;

import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateViewHolder extends ListViewHolder<Particulate> {



    public ParticulateViewHolder( View itemView ) {
        super( itemView );
        ButterKnife.bind( this, itemView );
    }

    @Override
    public void bind( Particulate data ) {

    }
}
