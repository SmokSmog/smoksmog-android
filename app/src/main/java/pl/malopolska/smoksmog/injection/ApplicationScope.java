package pl.malopolska.smoksmog.injection;

import dagger.Component;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;

@Component(
        modules = {
                ApplicationModule.class,
                GoogleApiClientModule.class
        }
)
public interface ApplicationScope {

}
