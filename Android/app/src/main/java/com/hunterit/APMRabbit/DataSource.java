package com.hunterit.APMRabbit;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class DataSource {

    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create(String location, String name, String timestamp) {

        //SQL Statement
        String insert = "INSERT INTO " + MySQLiteHelper.TABLE_NAME + " VALUES("
                + null + ","
                + "'" + location + "',"
                + "'" + timestamp + "',"
                + "'" + name + "')";

        //Load into the Database
        db.execSQL(insert);
        db.close();
    }


    public void rename(long id, String name) {

        String new_name = "'" + name + "'";

        String update = "UPDATE " + MySQLiteHelper.TABLE_NAME + " SET " + MySQLiteHelper.COL_NAME
                + " = " + new_name + " WHERE " + MySQLiteHelper.COL_ID + "=" + id;

        db.execSQL(update);
        db.close();
    }

    //UPDATE COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;

    public void delete (long id)
    {
        String delete = "DELETE FROM "+ MySQLiteHelper.TABLE_NAME +
                        " WHERE " + MySQLiteHelper.COL_ID + " = " + id;
        db.execSQL(delete);
        db.close();
    }

    public List<WayPoints> getAllWaypoints() {

        List<WayPoints> waypoints = new ArrayList<WayPoints>();

        String select = "SELECT * FROM " + MySQLiteHelper.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            WayPoints result = save(c);
            waypoints.add(result);
            c.moveToNext();
        }
        // Make sure to close the cursor
        c.close();
        return waypoints;
    }

    private WayPoints save(Cursor cursor) {
        WayPoints entry = new WayPoints();
        entry.setID(cursor.getLong(0));
        entry.setLocation(cursor.getString(1));
        entry.setTimestamp(cursor.getString(2));
        entry.setName(cursor.getString(3));
        return entry;
    }

}