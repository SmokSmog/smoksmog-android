package pl.malopolska.smoksmog.database.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class StationTable implements Table {

    public static final String NAME = "station";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_HASH = "hash";

    public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + NAME + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME + " TEXT NOT NULL," +
            COLUMN_LATITUDE + " REAL NOT NULL," +
            COLUMN_LONGITUDE  + " REAL NOT NULL," +
            COLUMN_HASH + " INTEGER UNIQUE NOT NULL" +
            ")";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + NAME;

    @Override
    public void create( SQLiteDatabase database ) {
        database.execSQL( SQL_CREATE );
    }

    @Override
    public void drop( SQLiteDatabase database ) {
        database.execSQL( SQL_DROP );
    }
}
