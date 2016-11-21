package com.serli.myhealthpartner.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDAO {

    private Database database;
    private SQLiteDatabase db;
    private String[] allColumns = { /* TODO  */};

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
        // TODO
    }

    public ProfileData getProfile() {
        // TODO
        return null;
    }

    private ProfileData cursoToData(Cursor cursor) {
        // TODO
        return null;
    }

}
