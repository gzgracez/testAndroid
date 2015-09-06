package now.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

/**
 * Handles the creation and deletion of the database
 * Call getReadableDatabase() or getWritableDatabase() on an instance of this class to get a SQLiteDatabase object
 * SQLiteDatabase provides  insert(), update(), delete(), and execSQL() for writing to database;
 *                          rawQuery() and query() for reading, which return a Cursor object
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoneyApp.db";
    /**
     * constants that specify table and column names
     */
    public static final String TABLE_TRANS = "Transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATETIME = "datetime";

//  CREATE TABLE Transactions (
//      _id INTEGER PRIMARY KEY AUTOINCREMENT,
//      value REAL NOT NULL,
//      datetime TEXT NULL
//  )
    private static final String SQL_CREATE_TABLES =
            "CREATE TABLE " + TABLE_TRANS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AMOUNT + " REAL NOT NULL, " +
                    COLUMN_DATETIME + " TEXT NULL" +
                    " )";
    private static final String SQL_DELETE_DATABASE =
            "DROP TABLE IF EXISTS " + TABLE_TRANS;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.d(SQLiteHelper.class.getName(), "creating database from string: " + SQL_CREATE_TABLES);
//        db.execSQL(SQL_CREATE_DATABASE);
        db.execSQL(SQL_CREATE_TABLES);
        Log.d(SQLiteHelper.class.getName(), "created database");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}