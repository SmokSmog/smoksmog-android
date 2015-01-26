package pl.malopolska.smoksmog.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmogApplication;

@Module(
        library = true
)
@Singleton
public class ApplicationModule {

    private final SmokSmogApplication application;

    public ApplicationModule(SmokSmogApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }
}
