package com.example.groupmelocator.app;


/*
  created by eric 6/5/2014

 * used as a template:
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {


    // this class is basically the schema for the sql database used in this application
    public static final String TABLE_NAME = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOCATIONNAME = "locationname";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_RADIUS = "radius";

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LOCATIONNAME  + " text not null, "
            + COLUMN_LATITUDE + " real, "
            + COLUMN_LONGITUDE + " real, "
            + COLUMN_RADIUS + " real"
            + " );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
