package com.antyzero.smoksmog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.antyzero.smoksmog.database.model.StationDbModel;

public class SmokSmogSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SmokSmog";

    public SmokSmogSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(StationDbModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
