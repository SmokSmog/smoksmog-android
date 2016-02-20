package com.antyzero.smoksmog.ui.screen.order.dialog;


import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                SupportFragmentModule.class
        }
)
public interface AddStationDialogComponent {

    void inject( AddStationDialog addStationDialog );
}
