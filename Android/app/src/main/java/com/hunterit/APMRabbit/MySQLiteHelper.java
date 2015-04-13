package com.hunterit.APMRabbit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //Declare the names so they will be used correctly everywhere
    public static final String TABLE_NAME = "waypoints";
    public static final String COL_ID = "id";
    public static final String COL_LOCATION = "location";
    public static final String COL_TIMESTAMP = "timestamp";
    public static final String COL_NAME = "name";

    //Declare file name and version number
    private static final String DATABASE_NAME = "waypoints.db";
    private static final int DATABASE_VERSION = 1;

    //Create Database Statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
             + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + COL_LOCATION + " VARCHAR, "
             + COL_TIMESTAMP + " VARCHAR, "
             + COL_NAME + " VARCHAR);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    //Upgrade Database for Version 2
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
