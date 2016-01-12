package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.ui.screen.settings.GeneralSettingsFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                FragmentModule.class,
        }
)
public interface FragmentComponent {

    void inject( GeneralSettingsFragment generalSettingsFragment );
}
