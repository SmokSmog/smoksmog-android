package com.antyzero.smoksmog.database;

import android.content.ContentValues;

import com.antyzero.smoksmog.database.model.ListItemDb;
import com.squareup.sqlbrite.BriteDatabase;

import pl.malopolska.smoksmog.model.Station;
import rx.Observable;

public class SmokSmokDb {

    private final BriteDatabase briteDatabase;

    public SmokSmokDb(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public Observable<ListItemDb> getList() {
        return briteDatabase.createQuery(ListItemDb.TABLE_NAME, "SELECT * FROM " + ListItemDb.TABLE_NAME)
                .flatMap(query -> query.asRows(ListItemDb.MAPPER::map));
    }

    public void addToList(Station station) {
        addToList(ListItemDb.FACTORY.marshal()
                ._id(station.getId())
                .position(Long.MAX_VALUE)
                .visible(true));
    }

    public void addToList(ListItemDb.Marshal marshal) {
        briteDatabase.insert(ListItemDb.TABLE_NAME, marshal.asContentValues());
        briteDatabase.createQuery(ListItemDb.TABLE_NAME, "SELECT * FROM " + ListItemDb.TABLE_NAME + " ORDER BY " + ListItemDb.POSITION + " DESC LIMIT 1")
                .flatMap(query -> query.asRows(ListItemDb.MAPPER::map))
                .map(listItemDb -> marshal.position(listItemDb.position() + 1));
    }

    /**
     * Should check if positions numbers are continuous
     */
    private Observable<Boolean> cleanUpPositions() {
        return getList()
                .reduce((first, second) -> {
                    if (second.position() - first.position() != 1) {
                        ContentValues contentValues = ListItemDb.FACTORY.marshal(second)
                                .position(first.position() + 1) // next position, relative to previous element
                                .asContentValues();
                        briteDatabase.update(ListItemDb.TABLE_NAME, contentValues, ListItemDb._ID + "=" + second._id());
                    }
                    return second;
                })
                .map(listItemDb -> true);

    }
}
