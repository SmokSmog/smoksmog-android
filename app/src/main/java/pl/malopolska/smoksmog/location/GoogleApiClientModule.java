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
    public GoogleApiClient provideGoogleApiClient( Context context ) {

        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
    }
}
