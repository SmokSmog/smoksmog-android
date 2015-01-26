package pl.malopolska.smoksmog.injection;

import android.content.Context;

import dagger.Component;

@Component(
        dependencies = {
                Context.class
        }
)
public interface ActivityComponent {

}
