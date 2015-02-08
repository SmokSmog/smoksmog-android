package pl.malopolska.smoksmog.injection;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

import dagger.Component;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.SmokSmogApiModule;

@Component(
        modules = {
                ApplicationModule.class,
                GoogleApiClientModule.class,
        }
)
public interface ApplicationComponent {

    Context context();

    Locale locale();

    // Injections

    void inject( BaseActivity baseActivity );
}
