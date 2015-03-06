package pl.malopolska.smoksmog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.malopolska.smoksmog.database.table.StationTable;
import pl.malopolska.smoksmog.database.table.Table;

public class SmogSmokDatabaseHelper extends SQLiteOpenHelper {

    // Registered tables
    private static final Table[] TABLES = new Table[]{
            new StationTable()
    };

    public static final String DATABASE_NAME = "smoksmog.db";
    public static final int DATABASE_VERSION = 1;

    public SmogSmokDatabaseHelper( Context context ) {
        this( context, DATABASE_VERSION);
    }

    SmogSmokDatabaseHelper( Context context, int version ) {
        super( context, DATABASE_NAME, null, version );
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        for( Table table : TABLES ){
            table.create( database );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        for( Table table : TABLES ){
            table.drop( database );
        }

        onCreate( database );
    }
}
