package pl.malopolska.smoksmog.data;

import dagger.Module;
import dagger.Provides;
import pl.malopolska.smoksmog.network.SmokSmogAPI;
import pl.malopolska.smoksmog.network.SmokSmogAPIImpl;

@Module(
        library = true
)
public class NetworkModule {

    private static final String ENDPOINT = "http://api.smoksmog.jkostrz.name/";

    @Provides
    public SmokSmogAPI provideSmokSmogAPI() {
        return SmokSmogAPIImpl.create(ENDPOINT);
    }
}
