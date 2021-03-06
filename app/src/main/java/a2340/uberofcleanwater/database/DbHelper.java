package a2340.uberofcleanwater.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class extending SQLiteOpenHelper which handles opening the database
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-28
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Database.db";

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + DbContract.UserEntry.TABLE_NAME + " (" +
                    DbContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    DbContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE + " INTEGER," +
                    DbContract.UserEntry.COLUMN_NAME_NAME + " TEXT," +
                    DbContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    DbContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    DbContract.UserEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    DbContract.UserEntry.COLUMN_NAME_TITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + DbContract.UserEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_WATERREPORTS =
            "CREATE TABLE " + DbContract.WaterReportEntry.TABLE_NAME + " (" +
                    DbContract.WaterReportEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.WaterReportEntry.COLUMN_NAME_DATE + " TEXT," +
                    DbContract.WaterReportEntry.COLUMN_NAME_AUTHOR + " TEXT," +
                    DbContract.WaterReportEntry.COLUMN_NAME_LONG + " REAL," +
                    DbContract.WaterReportEntry.COLUMN_NAME_LAT + " REAL," +
                    DbContract.WaterReportEntry.COLUMN_NAME_TYPE + " INTEGER," +
                    DbContract.WaterReportEntry.COLUMN_NAME_CONDITION + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_WATERREPORTS =
            "DROP TABLE IF EXISTS " + DbContract.WaterReportEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_PURITYREPORTS =
            "CREATE TABLE " + DbContract.PurityEntry.TABLE_NAME + " (" +
                    DbContract.PurityEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.PurityEntry.COLUMN_NAME_DATE + " TEXT," +
                    DbContract.PurityEntry.COLUMN_NAME_AUTHOR + " TEXT," +
                    DbContract.PurityEntry.COLUMN_NAME_LONG + " REAL," +
                    DbContract.PurityEntry.COLUMN_NAME_LAT + " REAL," +
                    DbContract.PurityEntry.COLUMN_NAME_CONTAMINANT + " TEXT," +
                    DbContract.PurityEntry.COLUMN_NAME_VIRUS + " TEXT," +
                    DbContract.PurityEntry.COLUMN_NAME_CONDITION + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_PURITYREPORTS =
            "DROP TABLE IF EXISTS " + DbContract.PurityEntry.TABLE_NAME;

    /**
     * Constructor for the Database helper
     * @param context the current context of the application
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_WATERREPORTS);
        db.execSQL(SQL_CREATE_ENTRIES_PURITYREPORTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_WATERREPORTS);
        db.execSQL(SQL_DELETE_ENTRIES_PURITYREPORTS);
        onCreate(db);
    }
}
