package pl.malopolska.smoksmog.location;

import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Component;

@Component(
        modules = {
                GoogleApiClientModule.class
        }
)
public interface GoogleApiComponent {

    public GoogleApiClient googleApi();
}
