package pl.malopolska.smoksmog.location;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module(
        library = false
)
public class GoogleApiClientModule {

    @Provides
    public GoogleApiClient provideGoogleApiClient(Context context) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();

        if (googleApiClient == null) {
            throw new IllegalStateException("Missing Google API client");
        }

        return googleApiClient;
    }
}
