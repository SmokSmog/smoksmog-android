package pl.malopolska.smoksmog.network;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

@Module
public class SmokSmogApiModule {

    private static final String ENDPOINT = "http://api.smoksmog.jkostrz.name/";

    @Provides
    public SmokSmogAPI smokSmogAPI(Locale locale){
        return SmokSmogAPIImpl.create(ENDPOINT, locale);
    }
}
