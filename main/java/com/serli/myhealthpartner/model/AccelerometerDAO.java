package com.serli.myhealthpartner.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccelerometerDAO {

    private Database database;
    private SQLiteDatabase db;
    private String[] allColumns = {database.ACC_TSTMP, database.ACC_X, database.ACC_Y, database.ACC_Z, database.ACC_ACTIVITY};

    public AccelerometerDAO(Context context) {
        this.database = new Database(context);
    }

    public void open() {
        db = database.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void addEntry(float x, float y, float z, long timestamp, int activity) {

        ContentValues values = new ContentValues();
        values.put(database.ACC_TSTMP, timestamp);
        values.put(database.ACC_X, x);
        values.put(database.ACC_Y, y);
        values.put(database.ACC_Z, z);
        values.put(database.ACC_ACTIVITY, activity);

        db.insert(database.ACC_TABLE, null, values);
    }

    public List<AccelerometerData> getDatas() {

        List<AccelerometerData> acce_data_list = new ArrayList<AccelerometerData>();
        Cursor cursor = db.query(database.ACC_TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AccelerometerData acce_data = cursorToData(cursor);
            acce_data_list.add(acce_data);
            cursor.moveToNext();
        }
        return acce_data_list;
    }

    public void deleteDatas() {
        db.delete(database.ACC_TABLE, null , null);
    }

    private AccelerometerData cursorToData(Cursor cursor) {
        AccelerometerData acce_data = new AccelerometerData();
        acce_data.setTimestamp(cursor.getInt(0));
        acce_data.setX(cursor.getFloat(1));
        acce_data.setY(cursor.getFloat(2));
        acce_data.setZ(cursor.getFloat(3));
        acce_data.setActivity(cursor.getInt(4));

        return acce_data;
    }
}
