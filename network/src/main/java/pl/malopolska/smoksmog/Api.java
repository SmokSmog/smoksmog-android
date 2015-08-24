package pl.malopolska.smoksmog;

import java.util.List;

import pl.malopolska.smoksmog.model.Station;
import retrofit.http.GET;
import rx.Observable;

public interface Api {

    @GET( "/stations" )
    Observable<List<Station>> stations();
}
