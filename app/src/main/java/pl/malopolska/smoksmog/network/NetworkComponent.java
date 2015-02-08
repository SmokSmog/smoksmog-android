package pl.malopolska.smoksmog.network;

import dagger.Component;
import pl.malopolska.smoksmog.injection.ApplicationComponent;
import pl.malopolska.smoksmog.toolbar.ToolbarController;

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

    void inject(ToolbarController toolbarController);
}
