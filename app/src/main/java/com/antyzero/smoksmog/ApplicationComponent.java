package com.antyzero.smoksmog;

import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.ui.ActivityModule;
import com.antyzero.smoksmog.ui.HistoryActivityComponent;
import com.antyzero.smoksmog.ui.MainActivityComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                NetworkModule.class
        }
)
public interface ApplicationComponent {

    MainActivityComponent plus( ActivityModule activityModule, GoogleModule googleModule );

    HistoryActivityComponent plus( ActivityModule activityModule );
}
