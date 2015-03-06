package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import dagger.Module;
import dagger.Provides;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;

@Module
public class JsonModule {

    @Provides
    public ObjectMapper provideObjectMapper(){

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule( new JodaModule() );

        return objectMapper;
    }

    @Provides
    public Converter provideConverter( ObjectMapper objectMapper ){
        return new JacksonConverter( objectMapper );
    }
}
