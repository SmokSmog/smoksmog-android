package pl.malopolska.smoksmog;


import java.util.Collection;
import java.util.List;

import pl.malopolska.smoksmog.model.Particulate;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class ApiUtils {

    private ApiUtils() {
        throw new IllegalAccessError();
    }

    public static Observable<Particulate> sortParticulates( Collection<Particulate> particulates ) {

        return Observable.from( particulates )
                .toSortedList( new Func2<Particulate, Particulate, Integer>() {
                    @Override
                    public Integer call( Particulate first, Particulate second ) {
                        return first.getPosition() - second.getPosition();
                    }
                } )
                .flatMap( new Func1<List<Particulate>, Observable<Particulate>>() {
                    @Override
                    public Observable<Particulate> call( List<Particulate> particulates ) {
                        return Observable.from( particulates );
                    }
                } );
    }
}
