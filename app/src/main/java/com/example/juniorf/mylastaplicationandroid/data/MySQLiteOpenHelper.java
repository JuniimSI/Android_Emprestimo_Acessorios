package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juniorf on 25/11/16.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "locations";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableLocation = "CREATE TABLE location ("+
                "id INTEGER PRIMARY KEY NOT NULL,"+
                "lat NUMERIC,"+
                "lng NUMERIC"+
                ");";
        db.execSQL(tableLocation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
