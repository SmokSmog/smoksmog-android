package com.antyzero.smoksmog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.antyzero.smoksmog.database.model.ListItemDb;

public class SmokSmogSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "SmokSmog";

    public SmokSmogSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ListItemDb.CREATE_TABLE);

        // Initial data

        sqLiteDatabase.insert(ListItemDb.TABLE_NAME, null, ListItemDb.FACTORY.marshal()
                ._id(0)
                .position(0)
                .visible(false)
                .asContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
