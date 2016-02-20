package com.antyzero.smoksmog;

import com.antyzero.smoksmog.fabric.FabricModule;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.logger.LoggerModule;
import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.settings.SettingsModule;
import com.antyzero.smoksmog.sync.SyncServiceComponent;
import com.antyzero.smoksmog.ui.ServiceModule;
import com.antyzero.smoksmog.ui.screen.ActivityComponent;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.main.MainActivityComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                LoggerModule.class,
                NetworkModule.class,
                FabricModule.class,
                SettingsModule.class
        }
)
public interface ApplicationComponent {

    MainActivityComponent plus( ActivityModule activityModule, GoogleModule googleModule );

    ActivityComponent plus( ActivityModule activityModule );

    SyncServiceComponent plus(ServiceModule serviceModule);

    void inject( SmokSmogApplication smokSmogApplication );

    void inject( StationIdList longs );
}
