package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by juniorf on 25/11/16.
 */

public abstract class AbstractDAO<T> {
    private Context context;
    protected MySQLiteOpenHelper mySQLiteOpenHelper;

    public abstract void insert(T t);

    public abstract void update(T t);

    public abstract void remove(int id);

    public abstract T findById(int id);

    public abstract ArrayList<T> findAll();

    public AbstractDAO(Context context){
        this.mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public Context getContext(){
        return this.context;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void close(){
        this.mySQLiteOpenHelper.close();
    }
}
