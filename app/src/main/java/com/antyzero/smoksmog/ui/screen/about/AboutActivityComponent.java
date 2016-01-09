package com.antyzero.smoksmog.ui.screen.about;

import com.antyzero.smoksmog.ui.screen.ActivityModule;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ActivityModule.class,
        }
)
public interface AboutActivityComponent {
    void inject( AboutActivity activity );
}
