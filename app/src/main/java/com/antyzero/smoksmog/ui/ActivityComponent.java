package com.antyzero.smoksmog.ui;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ActivityModule.class
        }
)
public interface ActivityComponent {

        void inject( MainActivity mainActivity );
}
