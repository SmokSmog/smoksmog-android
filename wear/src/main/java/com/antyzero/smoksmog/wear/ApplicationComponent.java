package com.antyzero.smoksmog.wear;

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

        void inject( MainActivity mainActivity );
}
