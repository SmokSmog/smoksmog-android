package com.antyzero.smoksmog;

import com.antyzero.smoksmog.eventbus.EventBusModule;
import com.antyzero.smoksmog.fabric.FabricModule;
import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.logger.LoggerModule;
import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.settings.SettingsModule;
import com.antyzero.smoksmog.time.TimeModule;
import com.antyzero.smoksmog.ui.dialog.AboutDialog;
import com.antyzero.smoksmog.ui.screen.ActivityComponent;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.main.MainActivityComponent;
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewHolder;
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewHolder;
import com.antyzero.smoksmog.ui.screen.start.model.StationIdList;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                LoggerModule.class,
                NetworkModule.class,
                FabricModule.class,
                SettingsModule.class,
                EventBusModule.class,
                TimeModule.class
        }
)
public interface ApplicationComponent {

    MainActivityComponent plus( ActivityModule activityModule, GoogleModule googleModule );

    ActivityComponent plus( ActivityModule activityModule );

    void inject( SmokSmogApplication smokSmogApplication );

    void inject( StationIdList longs );

    void inject( AirQualityViewHolder airQualityViewHolder );

    void inject( AboutDialog aboutDialog );

    void inject( ParticulateViewHolder particulateViewHolder );
}
