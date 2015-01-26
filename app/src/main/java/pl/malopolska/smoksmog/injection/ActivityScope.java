package pl.malopolska.smoksmog.injection;

import dagger.Component;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;

@Component(
        dependencies = {
                GoogleApiClientModule.class
        }
)
public interface ActivityScope {


}
