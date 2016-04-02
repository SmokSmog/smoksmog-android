package com.antyzero.smoksmog.user;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module @Singleton
public class UserModule {

    @Provides @Singleton
    public User provideUser(Context context){
        return new User(context);
    }
}
