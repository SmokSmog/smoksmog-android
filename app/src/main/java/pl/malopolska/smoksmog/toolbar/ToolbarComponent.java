package pl.malopolska.smoksmog.toolbar;

import dagger.Component;
import pl.malopolska.smoksmog.injection.ActivityComponent;

@Component(
        dependencies = {
                ActivityComponent.class
        }
)
public interface ToolbarComponent {

    void inject(ToolbarController toolbarController);
}
