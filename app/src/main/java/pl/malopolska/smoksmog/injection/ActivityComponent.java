package pl.malopolska.smoksmog.injection;

import android.app.Activity;

import dagger.Component;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.SmokSmogAPI;

@Component(
        modules = {
                ActivityModule.class
        },
        dependencies = {
                ApplicationComponent.class
        }
)
public interface ActivityComponent {

    // Provide

    public Activity activity();
}
