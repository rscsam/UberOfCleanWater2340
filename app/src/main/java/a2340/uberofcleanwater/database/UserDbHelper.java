package a2340.uberofcleanwater.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import a2340.uberofcleanwater.database.UserContract;

/**
 * Class extending SQLiteOpenHelper which handles opening the database
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-28
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE + " INTEGER," +
                    UserContract.UserEntry.COLUMN_NAME_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_TITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;


    /**
     * Constructor for the Database helper
     * @param context the current context of the application
     */
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the tables with their entries when the database is created
     * @param db the database being created
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * On upgrade, this method deletes the old database and creates a new one
     * @param db the database being upgraded
     * @param oldVersion the old version number
     * @param newVersion the new version number
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
