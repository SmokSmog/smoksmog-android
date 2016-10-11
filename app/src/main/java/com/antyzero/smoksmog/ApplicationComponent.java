package com.antyzero.smoksmog;

import com.antyzero.smoksmog.database.DatabaseModule;
import com.antyzero.smoksmog.eventbus.EventBusModule;
import com.antyzero.smoksmog.fabric.FabricModule;
import com.antyzero.smoksmog.logger.LoggerModule;
import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.settings.SettingsModule;
import com.antyzero.smoksmog.time.TimeModule;
import com.antyzero.smoksmog.ui.dialog.AboutDialog;
import com.antyzero.smoksmog.ui.dialog.FacebookDialog;
import com.antyzero.smoksmog.ui.screen.ActivityComponent;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewHolder;
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewHolder;
import com.antyzero.smoksmog.ui.screen.start.model.StationIdList;
import com.antyzero.smoksmog.ui.widget.StationWidget;
import com.antyzero.smoksmog.ui.widget.WidgetModule;
import com.antyzero.smoksmog.user.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                DatabaseModule.class,
                LoggerModule.class,
                NetworkModule.class,
                FabricModule.class,
                SettingsModule.class,
                EventBusModule.class,
                TimeModule.class,
                UserModule.class,
                WidgetModule.class
        }
)
public interface ApplicationComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(SmokSmogApplication smokSmogApplication);

    void inject(StationIdList longs);

    void inject(AirQualityViewHolder airQualityViewHolder);

    void inject(AboutDialog aboutDialog);

    void inject(ParticulateViewHolder particulateViewHolder);

    void inject(FacebookDialog facebookDialog);

    void inject(StationWidget stationWidget);
}
