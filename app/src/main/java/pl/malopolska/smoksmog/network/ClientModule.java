package pl.malopolska.smoksmog.network;

import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module
public class ClientModule {

    @Provides
    public Client provideClient(){
        return new OkClient(getOkHttpClient());
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }
}
