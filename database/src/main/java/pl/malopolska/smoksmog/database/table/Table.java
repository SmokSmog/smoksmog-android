package pl.malopolska.smoksmog.database.table;

import android.database.sqlite.SQLiteDatabase;

public interface Table {

    void create(SQLiteDatabase database);

    void drop(SQLiteDatabase database);
}
