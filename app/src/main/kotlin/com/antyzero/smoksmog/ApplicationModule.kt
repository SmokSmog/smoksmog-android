package com.antyzero.smoksmog

import android.app.Application
import android.content.Context

import com.antyzero.smoksmog.permission.PermissionHelper
import com.antyzero.smoksmog.tracking.Tracking
import com.antyzero.smoksmog.ui.typeface.TypefaceProvider

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Singleton
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideTypefaceProvider(context: Context): TypefaceProvider {
        return TypefaceProvider(context)
    }

    @Provides
    @Singleton
    internal fun provideTracker(context: Context): Tracking {
        return Tracking(context)
    }

    @Provides
    @Singleton
    internal fun providePermissionHelper(context: Context): PermissionHelper {
        return PermissionHelper(context)
    }
}
