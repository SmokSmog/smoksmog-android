package com.antyzero.smoksmog.ui.screen.main;

import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.ui.ActivityModule;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ActivityModule.class,
                GoogleModule.class
        }
)
public interface MainActivityComponent {

        void inject( MainActivity mainActivity );
}
