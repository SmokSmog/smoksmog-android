package com.antyzero.smoksmog.ui.screen.start;


import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.ui.screen.FragmentModule;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                FragmentModule.class,
                GoogleModule.class
        }
)
public interface StationFragmentComponent {

    void inject( StationFragment stationFragment );
}
