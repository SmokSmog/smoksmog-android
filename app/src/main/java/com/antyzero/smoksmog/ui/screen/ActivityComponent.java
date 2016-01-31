package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.ui.screen.about.AboutActivity;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ActivityModule.class,
        }
)
public interface ActivityComponent {

    FragmentComponent plus( FragmentModule fragmentModule );

    void inject( HistoryActivity activity );

    void inject( AboutActivity activity );
}
