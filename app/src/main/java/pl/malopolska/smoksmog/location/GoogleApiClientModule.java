package pl.malopolska.smoksmog.location;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleApiClientModule {

    private final Context context;

    public GoogleApiClientModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public GoogleApiClient provideGoogleApiClient() {
        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
    }
}
