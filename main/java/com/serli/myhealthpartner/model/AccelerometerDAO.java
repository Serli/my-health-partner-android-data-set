package com.serli.myhealthpartner.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AccelerometerDAO {

    private Database database;
    private SQLiteDatabase db;
    private String[] allColumns = { /* TODO  */};

    public AccelerometerDAO(Context context) {
        this.database = new Database(context);
    }

    public void open() {
        db = database.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void addEntry(float x, float y, float z, long timestamp) {
        // TODO
    }

    public List<AccelerometerData> getDatas() {
        // TODO
        return null;
    }

    public void deleteDatas() {
        // TODO
    }

    private AccelerometerData cursoToData(Cursor cursor) {
        // TODO
        return null;
    }
}
