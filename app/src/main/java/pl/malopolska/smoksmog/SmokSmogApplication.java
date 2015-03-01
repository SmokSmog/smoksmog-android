package pl.malopolska.smoksmog;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import pl.malopolska.smoksmog.injection.ApplicationComponent;
import pl.malopolska.smoksmog.injection.ApplicationModule;
import pl.malopolska.smoksmog.injection.Dagger_ApplicationComponent;
import pl.malopolska.smoksmog.network.Dagger_NetworkComponent;
import pl.malopolska.smoksmog.network.NetworkComponent;

/**
 * Project Application class
 */
public class SmokSmogApplication extends Application {

    private ApplicationComponent applicationComponent;
    private NetworkComponent networkComponent;

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

        networkComponent = Dagger_NetworkComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public static SmokSmogApplication get(@NonNull Context context) {
        return (SmokSmogApplication) context.getApplicationContext();
    }
}
