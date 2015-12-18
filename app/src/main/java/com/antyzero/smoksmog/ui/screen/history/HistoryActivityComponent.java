package com.antyzero.smoksmog.ui.screen.history;

import com.antyzero.smoksmog.ui.ActivityModule;

import dagger.Subcomponent;

@Subcomponent(
    modules = {
        ActivityModule.class,
    }
)
public interface HistoryActivityComponent {
    void inject(HistoryActivity activity);
}
