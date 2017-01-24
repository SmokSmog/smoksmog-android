package com.antyzero.smoksmog.user


import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class UserModule {

    @Provides
    @Singleton
    internal fun provideUser(context: Context): User = User(context)
}
