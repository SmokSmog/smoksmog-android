package com.antyzero.smoksmog.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.antyzero.smoksmog.database.model.ListItemDb;
import com.squareup.sqlbrite.BriteDatabase;

import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class SmokSmokDb {

    private final BriteDatabase briteDatabase;

    public SmokSmokDb(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public Observable<ListItemDb> getList() {
        return briteDatabase.createQuery(ListItemDb.TABLE_NAME, "SELECT * FROM " + ListItemDb.TABLE_NAME)
                .limit(1)
                .flatMap(query -> query.asRows(ListItemDb.MAPPER::map));
    }

    public void addToList(Station station) {
        addToList(ListItemDb.FACTORY.marshal()
                ._id(station.getId())
                .position(Long.MAX_VALUE)
                .visible(true));
    }

    public void addToList(ListItemDb.Marshal marshal) {
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        cleanUpListPositions();
        Cursor cursor = briteDatabase.query("SELECT * FROM " + ListItemDb.TABLE_NAME + " ORDER BY " + ListItemDb.POSITION + " DESC LIMIT 1");
        Observable.just(cursor)
                .flatMap(cursor1 -> asRows(cursor1, ListItemDb.MAPPER::map))
                .map(listItemDb -> marshal.position(listItemDb.position() + 1))
                .map(marshal1 -> briteDatabase.insert(ListItemDb.TABLE_NAME, marshal1.asContentValues(), SQLiteDatabase.CONFLICT_IGNORE))
                .map(aLong -> aLong > 0)
                .subscribe(
                        aBoolean -> transaction.markSuccessful(),
                        throwable -> transaction.end(),
                        transaction::end);
    }

    /**
     * Should check if positions numbers are continuous
     */
    private void cleanUpListPositions() {
        Cursor cursor = briteDatabase.query("SELECT * FROM " + ListItemDb.TABLE_NAME);
        Observable.just(cursor)
                .flatMap(cursor1 -> asRows(cursor1, ListItemDb.MAPPER::map))
                .reduce((first, second) -> {
                    if (second.position() - first.position() != 1) {
                        ContentValues contentValues = ListItemDb.FACTORY.marshal(second)
                                .position(first.position() + 1) // next position, relative to previous element
                                .asContentValues();
                        briteDatabase.update(ListItemDb.TABLE_NAME, contentValues, ListItemDb._ID + "=" + second._id());
                    }
                    return second;
                })
                .map(listItemDb -> true).subscribe();
    }

    private static <T> Observable<T> asRows(final Cursor cursor, final Func1<Cursor, T> mapper) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext() && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(mapper.call(cursor));
                        }
                    } finally {
                        cursor.close();
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }
}
