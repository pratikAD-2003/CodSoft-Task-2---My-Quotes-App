package com.example.myquotes.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "myQuotes";
    public static final String DB_TABLE = "myQuotesTable";
    public static final int DB_VERSION = 1;
    String query;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query = "create table " + DB_TABLE + "(id integer,quote text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        query = "drop table if exists " + DB_TABLE + "";
        db.execSQL(query);
    }
}
