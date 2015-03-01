package pl.malopolska.smoksmog.network;

import dagger.Component;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.injection.ApplicationComponent;
import pl.malopolska.smoksmog.toolbar.ToolbarController;
import pl.malopolska.smoksmog.ui.MainActivity;

@Component(
        modules = {
                JsonModule.class,
                SmokSmogApiModule.class,
                ClientModule.class
        },
        dependencies = {
                ApplicationComponent.class
        }
)
public interface NetworkComponent {

    void inject(MainActivity mainActivity);
}
