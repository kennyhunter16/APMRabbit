package com.hunterit.APMRabbit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments";
    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS locations (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "location TEXT, " +
            "timestamp TEXT, " +
            "name TEXT)";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //Create the Database Table
        database.execSQL(DATABASE_CREATE);

        ContentValues values = new ContentValues();

        values.put("location", "43.4254 x 25.254");
        values.put("timestamp", "April 4th, 2015 8:56am");
        values.put("name", "The House");
        database.insert("locations", "timestamp", values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}