package com.antyzero.smoksmog.ui;

import com.antyzero.smoksmog.google.GoogleModule;

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
