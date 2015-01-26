package pl.malopolska.smoksmog.injection;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Component;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.SmokSmogApiModule;

@Component(
        modules = {
                ApplicationModule.class,
                GoogleApiClientModule.class,
                SmokSmogApiModule.class
        }
)
public interface ApplicationComponent {

    Context context();

    GoogleApiClient googleApiClient();

    SmokSmogAPI smokSmogAPI();
}
