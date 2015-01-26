package pl.malopolska.smoksmog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import pl.malopolska.smoksmog.injection.ApplicationModule;
import pl.malopolska.smoksmog.injection.ApplicationComponent;
import pl.malopolska.smoksmog.injection.Dagger_ApplicationComponent;

/**
 * Project Application class
 */
public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics crashlytics = new Crashlytics.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();

        Fabric.with(this, crashlytics);

        applicationComponent = Dagger_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static SmokSmogApplication get(@NonNull Context context) {
        return (SmokSmogApplication) context.getApplicationContext();
    }
}
