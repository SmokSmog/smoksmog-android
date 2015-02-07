package pl.malopolska.smoksmog.network;

import dagger.Component;

@Component(
        modules = {
                JsonModule.class,
                SmokSmogApiModule.class
        }
)
public interface NetworkComponent {


}
