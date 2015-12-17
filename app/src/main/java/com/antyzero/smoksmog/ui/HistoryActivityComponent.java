package com.antyzero.smoksmog.ui;

import com.antyzero.smoksmog.google.GoogleModule;

import dagger.Subcomponent;

@Subcomponent(
    modules = {
        ActivityModule.class,
    }
)
public interface HistoryActivityComponent {
    void inject(HistoryActivity activity);
}
