package now.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;

/**
 * Handles the creation and deletion of the database
 * Call getReadableDatabase() or getWriteableDatabase() on an instance of this class to get a SQLiteDatabase object
 * SQLiteDatabase provides  insert(), update(), delete(), and execSQL() for writing to database;
 *                          rawQuery() and query() for reading, which return a Cursor object
 */

    public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Transactions.db";
    private DatabaseHelper bDbHelper;
    private SQLiteDatabase bDb;

    /**
     * constants that specify table and column names
     */
    public static final String TABLE_TRANS = "Transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DATETIME = "datetime";
    private final Context bCtx;
//    CREATE TABLE Transactions (
//        _id INTEGER PRIMARY KEY AUTOINCREMENT,
//        value INTEGER,
//        datetime TEXT
//    )
    private static final String SQL_CREATE_DATABASE =
            "CREATE TABLE " + TABLE_TRANS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VALUE + "INTEGER, " +
                    COLUMN_DATETIME + "TEXT" +
                    " )";
    private static final String SQL_DELETE_DATABASE =
            "DROP TABLE IF EXISTS " + TABLE_TRANS;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_DATABASE);
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
    public SQLiteHelper(Context context) {
        this.bCtx = context;
    }

    public SQLiteHelper open() throws SQLException {
        bDbHelper = new DatabaseHelper(bCtx);
        bDb = bDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (bDbHelper != null) bDbHelper.close();
    }

    public long createValue(String value, String date) {
        ContentValues allValues = new ContentValues();
        allValues.put(COLUMN_VALUE, value);
        allValues.put(COLUMN_DATETIME, date);
        return bDb.insert(TABLE_TRANS, null, allValues);
    }

    public Cursor fetchAllValues() {
        Cursor bCursor = bDb.query(TABLE_TRANS, new String[] {COLUMN_ID, COLUMN_VALUE, COLUMN_DATETIME}, null, null, null, null, null);
        if (bCursor != null) {
            bCursor.moveToFirst();
        }
        return bCursor;
    }
}
