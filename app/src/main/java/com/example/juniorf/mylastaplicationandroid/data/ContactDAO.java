package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juniorf on 24/10/16.
 */

public class ContactDAO {

    public static final String TBL_CONTACT = "contact";
    private static DBHelperC dbHelper = null;

    public static final String CONTACT_ID = "_id";
    public static final String CONTACT_NOME = "nome";
    public static final String CONTACT_ENDERECO = "endereco";
    public static final String CONTACT_TELEFONE = "telefone";
    private static final String campos[] = {"nome", "endereco", "telefone", "_id"};

    private static final String DATABASE_NAME = "BancoAndroid";
    private static final int DATABASE_VERSION = 1;
    private CursorAdapter dataSource;


    //Estrutura da tabela Agenda (sql statement)
    private static String CREATE_CONTACT = "CREATE TABLE contact(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT," +
            "telefone TEXT," +
            "endereco TEXT);";



    public ContactDAO(Context context) {
        if(dbHelper == null){
            dbHelper = new DBHelperC(context);
        }
    }


    public void insert(Contact c, SQLiteDatabase db){
        if(c != null){
            db.execSQL("INSERT INTO contact (nome, telefone, endereco) values ('"+c.getNome()+"', '"+c.getTelefone()+"', '"+c.getTelefone()+"'   );");
        }
    }

    public List<Contact> select(){
        List<Contact> list = new ArrayList<Contact>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from "+ TBL_CONTACT, null);
        try {
            while (c.moveToNext()) {
                Contact pessoa = new Contact();
                pessoa.setId((c.getColumnIndex("_id")));
                pessoa.setNome(c.getString(c.getColumnIndex("nome")));
                pessoa.setTelefone(c.getString(c.getColumnIndex("telefone")));
                pessoa.setEndereco(c.getString(c.getColumnIndex("endereco")));

                list.add(pessoa);
            }
        } finally {
            c.close();
        }

        db.close();

        return list;
    }

    public  ArrayList<String> getAllItens(){
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
