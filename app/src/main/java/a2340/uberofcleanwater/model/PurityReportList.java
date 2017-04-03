package a2340.uberofcleanwater.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import a2340.uberofcleanwater.database.DbContract;

/**
 * Controls purity report list in the database
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-03-09
 */
public class PurityReportList {

    /**
     * adds a report to the database
     * @param db the database being accessed
     * @param newReport the report to add
     */
    public static void addReport(SQLiteDatabase db, PurityReport newReport) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PurityEntry.COLUMN_NAME_AUTHOR, newReport.getAuthor());
        values.put(DbContract.PurityEntry.COLUMN_NAME_CONDITION , newReport.getCondition().ordinal());
        values.put(DbContract.PurityEntry.COLUMN_NAME_CONTAMINANT, newReport.getContaminantPPM());
        values.put(DbContract.PurityEntry.COLUMN_NAME_DATE, newReport.getDate().toString());
        values.put(DbContract.PurityEntry.COLUMN_NAME_LAT, newReport.getLatitude());
        values.put(DbContract.PurityEntry.COLUMN_NAME_LONG, newReport.getLongitude());
        values.put(DbContract.PurityEntry.COLUMN_NAME_VIRUS, newReport.getVirusPPM());

        Cursor c = db.query(DbContract.PurityEntry.TABLE_NAME, null, "_id = (SELECT MAX(_id) FROM " + DbContract.PurityEntry.TABLE_NAME + ")", null, null, null, null);

        if(c.moveToFirst()) {
            values.put(DbContract.PurityEntry._ID, c.getInt(c.getColumnIndex(DbContract.PurityEntry._ID)) + 1);
        } else {
            values.put(DbContract.PurityEntry._ID, 1);
        }
        c.close();

        db.insert(DbContract.PurityEntry.TABLE_NAME, null, values);
    }

    /**
     * Returns a list of purity reports
     * @param db the database being accessed
     * @return an ArrayList of purity reports
     */
    public static ArrayList<PurityReport> getReportList(SQLiteDatabase db) {
        ArrayList<PurityReport> list = new ArrayList<>();

        String[] projection = {
                DbContract.PurityEntry.COLUMN_NAME_AUTHOR,
                DbContract.PurityEntry._ID,
                DbContract.PurityEntry.COLUMN_NAME_DATE,
                DbContract.PurityEntry.COLUMN_NAME_LAT,
                DbContract.PurityEntry.COLUMN_NAME_LONG,
                DbContract.PurityEntry.COLUMN_NAME_CONDITION,
                DbContract.PurityEntry.COLUMN_NAME_CONTAMINANT,
                DbContract.PurityEntry.COLUMN_NAME_VIRUS

        };

        Cursor c = db.query(
                DbContract.PurityEntry.TABLE_NAME,  // The table to query
                projection,                         // The columns to return
                null,                          // The columns for the where clause
                null,                      // The values for the where clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // the sort order
        );

        while(c.moveToNext()) {
            String author = c.getString(c.getColumnIndex(DbContract.PurityEntry.COLUMN_NAME_AUTHOR));
            int num = c.getInt(c.getColumnIndex(DbContract.PurityEntry._ID));
            double latitude = c.getDouble(c.getColumnIndex(DbContract.PurityEntry.COLUMN_NAME_LAT));
            double longitude = c.getDouble(c.getColumnIndex(DbContract.PurityEntry.COLUMN_NAME_LONG));
            int virus = c.getInt(c.getColumnIndex((DbContract.PurityEntry.COLUMN_NAME_VIRUS)));
            PurityCondition condition = PurityCondition.values()[c.getInt(c.getColumnIndex(DbContract.PurityEntry.COLUMN_NAME_CONDITION))];
            int contaminant = c.getInt(c.getColumnIndex((DbContract.PurityEntry.COLUMN_NAME_CONTAMINANT)));

            String target = c.getString(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_DATE));
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy", Locale.US);
            Date result = new Date(); //if there is a problem reading the date, just set it to the current time
            try {
                result = df.parse(target);
            } catch (ParseException ex){
                ex.printStackTrace();
            }

            list.add(new PurityReport(author, longitude, latitude, condition, contaminant, virus, result, num));
        }
        c.close();

        return list;
    }
}
