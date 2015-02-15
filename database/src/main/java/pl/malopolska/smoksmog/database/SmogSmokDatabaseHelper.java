package pl.malopolska.smoksmog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmogSmokDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "smoksmog.db";

    public SmogSmokDatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
