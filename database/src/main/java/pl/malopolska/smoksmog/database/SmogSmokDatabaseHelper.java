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

    public SmogSmokDatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        for( Table table : TABLES ){
            table.create( database );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
