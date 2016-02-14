package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.ui.screen.order.dialog.AddStationDialog;
import com.antyzero.smoksmog.ui.screen.start.StationFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                SupportFragmentModule.class
        }
)
public interface SupportFragmentComponent {

    void inject( StationFragment stationFragment );

    void inject( AddStationDialog addStationDialog );
}
