package com.antyzero.smoksmog.common;

import com.antyzero.smoksmog.common.version.Change;
import com.antyzero.smoksmog.common.version.VersionCollection;

import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class VersionCheck {

    private final static SortingTransformer SORTING_TRANSFORMER = new SortingTransformer();

    private final Locale locale;
    private final VersionCollection versions;

    public VersionCheck(Locale locale, VersionCollection versions) {
        this.locale = locale;
        this.versions = versions;
    }

    private static class SortingTransformer implements Observable.Transformer<Change, Change> {

        @Override
        public Observable<Change> call(Observable<Change> changeObservable) {
            return changeObservable
                    .toSortedList(new Func2<Change, Change, Integer>() {
                        @Override
                        public Integer call(Change change, Change change2) {
                            return change.getType().ordinal() - change2.getType().ordinal();
                        }
                    })
                    .flatMap(new Func1<List<Change>, Observable<Change>>() {
                        @Override
                        public Observable<Change> call(List<Change> changes) {
                            return Observable.from(changes);
                        }
                    });
        }
    }
}
