package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juniorf on 21/10/16.
 */

public class DBHelper extends SQLiteOpenHelper  {

    private static String DB_NAME = "BancoAndroid";
    private static int VERSION = 1;
    private static String TABLE_ITENS = "CREATE TABLE itens(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT," +
            "detalhes TEXT);";
    private static String TABLE_CONTACT = "CREATE TABLE contact(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT," +
            "telefone TEXT," +
            "endereco TEXT);";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ITENS);
        db.execSQL(TABLE_CONTACT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
