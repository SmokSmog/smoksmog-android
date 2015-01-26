package pl.malopolska.smoksmog.injection;

import dagger.Component;
import pl.malopolska.smoksmog.base.BaseActivity;

@Component(
        dependencies = {
                ApplicationScope.class
        }
)
public interface ActivityScope {

}
