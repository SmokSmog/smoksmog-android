package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.ui.ActivityModule;
import com.antyzero.smoksmog.ui.screen.about.AboutActivity;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;

import dagger.Subcomponent;

@Subcomponent(
    modules = {
        ActivityModule.class,
    }
)
public interface ActivityComponent {

    void inject(HistoryActivity activity);

    void inject(AboutActivity activity);
}
