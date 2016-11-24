package com.serli.myhealthpartner.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public final static int VERSION = 1;

    public final static String PROFILE_TABLE = "Profile_DB";
    public final static String PROFILE_ID = "ID";
    public final static String PROFILE_WEIGHT = "Weight";
    public final static String PROFILE_HEIGHT = "Height";
    public final static String PROFILE_AGE = "Age";
    public final static String PROFILE_GENDER = "Gender";

    public final static String ACC_TABLE = "Accelero_DB";
    public final static String ACC_TSTMP = "Timestamp";
    public final static String ACC_X = "X_pos";
    public final static String ACC_Y = "Y_pos";
    public final static String ACC_Z = "Z_pos";
    public final static String ACC_IMEI = "IMEI";
    public final static String ACC_PROFILE_ID = "Profile_ID";

    public Database(Context context) {
        super(context, "myDB", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE + " ("
                + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROFILE_WEIGHT + " INTEGER NOT NULL,"
                + PROFILE_HEIGHT + " INTEGER NOT NULL,"
                + PROFILE_AGE + " DATE NOT NULL,"
                + PROFILE_GENDER + " INTEGER NOT NULL" + ");";

        String CREATE_ACC_TABLE = "CREATE TABLE " + ACC_TABLE + " ("
                + ACC_TSTMP + " INTEGER PRIMARY KEY,"
                + ACC_X + " FLOAT NOT NULL,"
                + ACC_Y + " FLOAT NOT NULL,"
                + ACC_Z + " FLOAT NOT NULL,"
                + ACC_IMEI + " INTEGER NOT NULL,"
                + ACC_PROFILE_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + ACC_PROFILE_ID + ") REFERENCES " + PROFILE_TABLE + "(" + PROFILE_ID + ")" + ");";

        sqLiteDatabase.execSQL(CREATE_PROFILE_TABLE);
        sqLiteDatabase.execSQL(CREATE_ACC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_PROFILE_TABLE = "DROP TABLE IF EXISTS " + PROFILE_TABLE + ";";
        String DROP_ACC_TABLE = "DROP TABLE IF EXISTS " + ACC_TABLE + ";";

        sqLiteDatabase.execSQL(DROP_PROFILE_TABLE);
        sqLiteDatabase.execSQL(DROP_ACC_TABLE);
        onCreate(sqLiteDatabase);
    }
}
