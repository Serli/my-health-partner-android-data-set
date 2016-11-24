package com.serli.myhealthpartner.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileDAO {

    private Database database;
    private SQLiteDatabase db;
    private String[] allColumns = {database.PROFILE_ID, database.PROFILE_HEIGHT, database.PROFILE_WEIGHT, database.PROFILE_AGE, database.PROFILE_GENDER};

    public ProfileDAO(Context context) {
        this.database = new Database(context);
    }

    public void open() {
        db = database.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void addEntry(ProfileData data) {

        ContentValues values = new ContentValues();
        values.put(database.PROFILE_HEIGHT, data.getSize());
        values.put(database.PROFILE_WEIGHT, data.getWeight());
        values.put(database.PROFILE_AGE, data.getBirthday().getTime());
        values.put(database.PROFILE_GENDER, data.getSex());

        db.insert(database.PROFILE_TABLE, null, values);
    }

    public ProfileData getProfile() {
        Cursor c = db.query(database.PROFILE_TABLE, allColumns, null, null, null, null, null);
        return cursorToData(c);
    }

    private ProfileData cursorToData(Cursor cursor) {
        cursor.moveToFirst();

        ProfileData prof_data = new ProfileData();
        prof_data.setSize(cursor.getInt(1));
        prof_data.setWeight(cursor.getInt(2));

        Date d = new Date(cursor.getLong(3));
        prof_data.setBirthday(d);
        prof_data.setSex(cursor.getInt(4));

        return prof_data;
    }

}
