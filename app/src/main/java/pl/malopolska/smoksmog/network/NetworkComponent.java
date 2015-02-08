package pl.malopolska.smoksmog.network;

import dagger.Component;
import pl.malopolska.smoksmog.injection.ApplicationComponent;

@Component(
        modules = {
                JsonModule.class,
                SmokSmogApiModule.class
        },
        dependencies = {
                ApplicationComponent.class
        }
)
public interface NetworkComponent {


}
