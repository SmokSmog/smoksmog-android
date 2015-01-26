package pl.malopolska.smoksmog.injection;

import android.content.Context;

import dagger.Component;

@Component(
        modules = {
                ApplicationModule.class
        }
)
public interface ApplicationScope {

    void incject(Context context);
}
