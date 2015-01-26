package pl.malopolska.smoksmog.injection;

import android.app.Activity;

import dagger.Component;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;

@Component(
        dependencies = {
                ApplicationScope.class
        },
        modules = {
                GoogleApiClientModule.class
        }
)
public interface ActivityScope {

    void inject(Activity activity);
}
