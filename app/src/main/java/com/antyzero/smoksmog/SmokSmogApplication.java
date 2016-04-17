package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.antyzero.smoksmog.sync.SyncService;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

import org.joda.time.Duration;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import smoksmog.logger.Logger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Inject
    Logger logger;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with( this, new CrashlyticsCore.Builder()
                .disabled( BuildConfig.DEBUG )
                .build(), new Answers() );

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

        applicationComponent.inject( this );

        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                .setDefaultFontPath( "fonts/Lato-Light.ttf" )
                .build() );

        Task syncTask = new PeriodicTask.Builder()
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setPersisted(true)
                .setRequiresCharging(false)
                .setUpdateCurrent(true)
                .setService(SyncService.class)
                .setPeriod(Duration.standardHours(1).getStandardSeconds())
                .setTag(SyncService.TAG)
                .build();

        GcmNetworkManager.getInstance(this).schedule(syncTask);
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    /**
     * Kept to inject mocked components.
     *
     * @param applicationComponent for replace
     */
    @VisibleForTesting
    public void setAppComponent( ApplicationComponent applicationComponent ) {
        this.applicationComponent = applicationComponent;
    }

    /**
     * Get access to application instance
     *
     * @param context
     * @return
     */
    public static SmokSmogApplication get( Context context ) {
        return ( SmokSmogApplication ) context.getApplicationContext();
    }
}
