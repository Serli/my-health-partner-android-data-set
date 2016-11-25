package com.serli.myhealthpartner.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Link between the profile table in the {@link Database} and the controllers.
 */
public class ProfileDAO {

    private Database database;
    private SQLiteDatabase db;
    private String[] allColumns = {database.PROFILE_ID, database.PROFILE_HEIGHT, database.PROFILE_WEIGHT, database.PROFILE_AGE, database.PROFILE_GENDER};

    /**
     * @param context The context where the dao is called.
     */
    public ProfileDAO(Context context) {
        this.database = new Database(context);
    }

    /**
     * Open the connection with the database.
     */
    public void open() {
        db = database.getWritableDatabase();
    }

    /**
     * Close the connection with the database.
     */
    public void close() {
        database.close();
    }

    /**
     * Add an entry in the profile table.<br/>
     * As only one profile is needed in the application, the table will be empty before the insertion.
     *
     * @param data The data object containing the profile information
     */
    public void addEntry(ProfileData data) {
        if (data.getBirthday() != null && data.getSize() != 0 && data.getWeight() != 0) {
            db.delete(Database.PROFILE_TABLE, null, null);

            ContentValues values = new ContentValues();
            values.put(Database.PROFILE_HEIGHT, data.getSize());
            values.put(Database.PROFILE_WEIGHT, data.getWeight());
            values.put(Database.PROFILE_AGE, data.getBirthday().getTime());
            values.put(Database.PROFILE_GENDER, data.getSex());

            db.insert(Database.PROFILE_TABLE, null, values);
        }
    }

    /**
     * Return a {@link ProfileData} object containing the profile stored.
     *
     * @return The profile.
     */
    public ProfileData getProfile() {
        Cursor c = db.query(Database.PROFILE_TABLE, allColumns, null, null, null, null, null);
        c.moveToFirst();
        return cursorToData(c);
    }

    private ProfileData cursorToData(Cursor cursor) {
        ProfileData prof_data = new ProfileData();
        prof_data.setSize(cursor.getInt(1));
        prof_data.setWeight(cursor.getInt(2));

        Date d = new Date(cursor.getLong(3));
        prof_data.setBirthday(d);
        prof_data.setSex(cursor.getInt(4));

        return prof_data;
    }

}
