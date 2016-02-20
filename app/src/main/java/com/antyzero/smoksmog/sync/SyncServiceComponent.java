package com.antyzero.smoksmog.sync;

import com.antyzero.smoksmog.ApplicationModule;
import com.antyzero.smoksmog.ui.ServiceModule;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ServiceModule.class
        }
)
public interface SyncServiceComponent {

    void inject(SyncService syncService);
}
