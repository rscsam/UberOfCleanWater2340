package a2340.uberofcleanwater.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import a2340.uberofcleanwater.database.DbContract;

/**
 * Adds new water report to report list
 *
 * @author Anna Bergstrom
 * @author Sylvia Necula
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-28
 */
public class ReportList {

    /**
     * adds a report to the list
     * @param newReport the report to add
     */
    public static void addReport(SQLiteDatabase db, WaterReport newReport) {
        ContentValues values = new ContentValues();
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_AUTHOR, newReport.getAuthor());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_NUM, newReport.getReportNum());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_DATE, newReport.getDate().toString());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_LAT, newReport.getLatitude());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_LONG, newReport.getLongitude());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_CONDITION, newReport.getCondition().ordinal());
        values.put(DbContract.WaterReportEntry.COLUMN_NAME_TYPE, newReport.getType().ordinal());

        db.insert(DbContract.WaterReportEntry.TABLE_NAME, null, values);
    }


    /**
     * Returns a list of water reports
     * @param db the database being accessed
     * @return an arraylist of water reports
     */
    public static ArrayList getReportList(SQLiteDatabase db) {
        ArrayList<WaterReport> list = new ArrayList<>();

        String[] projection = {
                DbContract.WaterReportEntry.COLUMN_NAME_AUTHOR,
                DbContract.WaterReportEntry.COLUMN_NAME_NUM,
                DbContract.WaterReportEntry.COLUMN_NAME_DATE,
                DbContract.WaterReportEntry.COLUMN_NAME_LAT,
                DbContract.WaterReportEntry.COLUMN_NAME_LONG,
                DbContract.WaterReportEntry.COLUMN_NAME_CONDITION,
                DbContract.WaterReportEntry.COLUMN_NAME_TYPE
        };

        Cursor c = db.query(
                DbContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                         // The columns to return
                null,                          // The columns for the where clause
                null,                      // The values for the where clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // the sort order
        );

        while(c.moveToNext()) {
            String author = c.getString(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_AUTHOR));
            int num = c.getInt(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_NUM));
            double latitude = c.getDouble(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_LAT));
            double longitude = c.getDouble(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_LONG));
            WaterCondition condition = WaterCondition.values()[c.getInt(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_CONDITION))];
            WaterType type = WaterType.values()[c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_TYPE)];

            String target = c.getString(c.getColumnIndex(DbContract.WaterReportEntry.COLUMN_NAME_DATE));
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy", Locale.US);
            Date result = new Date(); //if there is a problem reading the date, just set it to the current time
            try {
                result = df.parse(target);
            } catch (ParseException ex){
                ex.printStackTrace();
            }

            list.add(new WaterReport(author, longitude, latitude, type, condition, result, num));
        }
        c.close();

        return list;
    }
}
