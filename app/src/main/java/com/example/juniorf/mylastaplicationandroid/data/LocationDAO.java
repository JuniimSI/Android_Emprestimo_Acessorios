package com.example.juniorf.mylastaplicationandroid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.MapsActivity;

import java.util.ArrayList;


/**
 * Created by juniorf on 25/11/16.
 */

public class LocationDAO extends AbstractDAO<Location> {
    public LocationDAO(Context context) {
        super(context);
    }



    @Override
    public void insert(Location location) {
        SQLiteDatabase database = this.mySQLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        double s = location.getLatitude();
        double x = location.getLongitude();
        values.put("lat", s);
        values.put("lng", x);
        database.insert("location", null, values);
        Log.i("kaksdkasd", "TUDO CERTO");
        database.close();
    }

    @Override
    public void update(Location location) {

    }

    public ArrayList<Location> find(){
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase database = this.mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select lat, lng from location", null);
        Log.i("CURSOR", ""+cursor.toString());
        while(cursor.moveToNext()){
            Location location = new Location("");
            location.setLatitude(cursor.getFloat(0));
            location.setLongitude(cursor.getFloat(1));

            locations.add(location);
        }
        cursor.close();
        database.close();
        return locations;
    }

    @Override
    public void remove(int id) {
        SQLiteDatabase database = this.mySQLiteOpenHelper.getWritableDatabase();
        database.delete("location", "id=?", new String[]{String.valueOf(id)});
        database.close();
    }

    @Override
    public Location findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Location> findAll() {
        return null;
    }
}
