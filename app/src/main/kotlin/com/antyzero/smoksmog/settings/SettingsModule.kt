package com.antyzero.smoksmog.settings

import android.content.Context
import com.antyzero.smoksmog.permission.PermissionHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsHelper(context: Context, permissionHelper: PermissionHelper): SettingsHelper {
        return SettingsHelper(context, permissionHelper)
    }
}
