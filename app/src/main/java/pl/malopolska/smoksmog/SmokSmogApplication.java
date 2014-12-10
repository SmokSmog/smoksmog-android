package pl.malopolska.smoksmog;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Project Application class
 */
public class SmokSmogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics crashlytics = new Crashlytics.Builder()
                .disabled( BuildConfig.DEBUG )
                .build();

        Fabric.with( this, crashlytics );
    }
}
