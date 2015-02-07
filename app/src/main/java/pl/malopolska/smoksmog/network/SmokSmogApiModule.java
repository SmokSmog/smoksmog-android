package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.Converter;

@Module
public class SmokSmogApiModule {

    private static final String ENDPOINT = "http://api.smoksmog.jkostrz.name/";

    @Provides
    public SmokSmogAPI smokSmogAPI(Locale locale, Client client, Converter converter){

        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setClient( client )
                .setEndpoint( ENDPOINT )
                .setConverter( converter );

        return restAdapterBuilder.build().create( SmokSmogAPI.class );
    }
}
