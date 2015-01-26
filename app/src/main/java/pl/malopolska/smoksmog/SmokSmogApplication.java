package pl.malopolska.smoksmog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Project Application class
 */
public class SmokSmogApplication extends Application {

    private ApplicationScope applicationScope;

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics crashlytics = new Crashlytics.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();

        Fabric.with(this, crashlytics);

        applicationScope = Dagger_ApplicationScope.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationScope.incject(this);
    }

    public ApplicationScope getApplicationScope() {
        return applicationScope;
    }

    public static SmokSmogApplication get(@NonNull Context context) {
        return (SmokSmogApplication) context.getApplicationContext();
    }
}
