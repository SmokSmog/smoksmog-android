package com.antyzero.smoksmog.common;

import com.antyzero.smoksmog.common.version.Change;
import com.antyzero.smoksmog.common.version.VersionCollection;

import java.util.Locale;

import rx.Observable;

public class VersionCheck {

    private final static SortingTransformer SORTING_TRANSFORMER = new SortingTransformer();

    private final Locale locale;
    private final VersionCollection versions;

    public VersionCheck( Locale locale, VersionCollection versions ) {
        this.locale = locale;
        this.versions = versions;
    }

    private static class SortingTransformer implements Observable.Transformer<Change, Change> {

        @Override
        public Observable<Change> call( Observable<Change> changeObservable ) {
            return changeObservable
                    .toSortedList( ( change, change2 ) -> change.getType().ordinal() - change2.getType().ordinal() )
                    .flatMap( Observable::from );
        }
    }
}
