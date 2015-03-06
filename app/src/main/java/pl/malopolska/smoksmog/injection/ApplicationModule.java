package pl.malopolska.smoksmog.injection;

import android.content.Context;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.SmokSmogApplication;

@Module(
        library = true
)
public class ApplicationModule {

    private final SmokSmogApplication application;

    public ApplicationModule(SmokSmogApplication application) {
        this.application = application;
    }

    @Provides
    public Context provideContext(){
        return application;
    }

    @Provides
    public Locale provideLocale(Context context){
        return context.getResources().getConfiguration().locale;
    }
}
