package com.antyzero.smoksmog;

import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.ui.ActivityComponent;
import com.antyzero.smoksmog.ui.ActivityModule;

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

    ActivityComponent plus( ActivityModule activityModule );
}
