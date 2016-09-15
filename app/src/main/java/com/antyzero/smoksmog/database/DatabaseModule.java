package com.antyzero.smoksmog.database;


import android.database.sqlite.SQLiteOpenHelper;

import com.antyzero.smoksmog.BuildConfig;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import smoksmog.logger.Logger;

@Singleton
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public SqlBrite provideSqlBrite(Logger logger) {
        return SqlBrite.create(message -> {
            if (BuildConfig.DEBUG) {
                logger.i("SqlBrite", message);
            }
        });
    }

    @Provides
    @Singleton
    public SQLiteOpenHelper provideSqLiteOpenHelper(){

        return null;
    }

}
