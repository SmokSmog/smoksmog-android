package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.ui.screen.start.StationFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                SupportFragmentModule.class,
        }
)
public interface SupportFragmentComponent {

    void inject( StationFragment stationFragment );
}
