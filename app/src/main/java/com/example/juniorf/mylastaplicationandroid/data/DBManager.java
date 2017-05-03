package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by juniorf on 21/10/16.
 */

public class DBManager {
    private static DBHelper dbHelper = null;

    public DBManager(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context);
        }
    }

    public void addItem(String nome){
        String sql = "INSERT INTO itens (nome) values ('"+nome+"')";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    public void add(Peca peca){
        String sql = "INSERT INTO itens (nome, detalhes) values ('"+peca.getNome()+"', '"+peca.getDetalhes()+"')";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
    }


    public void getById(int id){
        String sql = "Select nome, telefone from itens where id="+id+";";
    }

    public ArrayList<String> getAllItens(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT nome FROM itens";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> itens = null;
        if(cursor != null && cursor.moveToFirst()){
            itens = new ArrayList<String>();
            do {
                itens.add(  cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return itens;
    }
    public ArrayList<String> getAllItensC(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT nome FROM contact";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> itens = null;
        if(cursor != null && cursor.moveToFirst()){
            itens = new ArrayList<String>();
            do {
                itens.add(  cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return itens;
    }

}
