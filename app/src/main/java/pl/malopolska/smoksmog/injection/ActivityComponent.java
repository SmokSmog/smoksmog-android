package pl.malopolska.smoksmog.injection;

import dagger.Component;
import pl.malopolska.smoksmog.base.BaseActivity;

@Component(
        dependencies = {
                ApplicationComponent.class
        }
)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
}
