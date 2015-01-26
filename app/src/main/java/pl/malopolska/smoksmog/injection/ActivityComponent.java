package pl.malopolska.smoksmog.injection;

import dagger.Component;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;

@Component(
        dependencies = {
                ApplicationComponent.class
        }
)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

    SmokSmogAPI smokSmogAPI();
}
