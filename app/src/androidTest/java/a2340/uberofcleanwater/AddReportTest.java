package a2340.uberofcleanwater;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import a2340.uberofcleanwater.database.DbContract;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterCondition;
import a2340.uberofcleanwater.model.WaterReport;
import a2340.uberofcleanwater.model.WaterType;

/**
 * JUnit test for addReport method in ReportList.java.
 * @author Sylvia Necula
 * @version 1.0
 * @since  4-5-17
 */
public class AddReportTest {
    private SQLiteDatabase db;
    private DbHelper mockDbHelper;
    private WaterReport report1;
    private WaterReport report2;

    @Before
    public void setup() {
        mockDbHelper = new DbHelper(InstrumentationRegistry.getTargetContext());
        db = mockDbHelper.getWritableDatabase();
        report1 = new WaterReport("Sally", 20.0, 20.0, WaterType.Bottled, WaterCondition.Potable);
        report2 = new WaterReport("Bob", 10.0, 10.0, WaterType.Spring, WaterCondition.TreatableClear);
    }

    @After
    public void finish() {
        //delete everything in the database between tests
        db.delete(DbContract.WaterReportEntry.TABLE_NAME, null, null);
    }

    @Test
    public void testOneReport() {
        ReportList.addReport(db, report1);
        ArrayList<WaterReport> list = ReportList.getReportList(db);
        boolean foundReport1 = false;
        for (WaterReport report: list) {
            if (report.getReportNum() == report1.getReportNum() + 1) {
                foundReport1 = true;
            }
        }
        Assert.assertTrue(foundReport1);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testTwoReports() {
        ReportList.addReport(db, report1);
        ReportList.addReport(db, report2);
        boolean foundReport1 = false;
        boolean foundReport2 = false;
        ArrayList<WaterReport> list = ReportList.getReportList(db);
        for (WaterReport report: list) {
            if (report.getReportNum() == report1.getReportNum() + 1) {
                foundReport1 = true;
            }
            if (report.getReportNum() == report2.getReportNum() + 2) {
                foundReport2 = true;
            }
        }
        Assert.assertTrue(foundReport1);
        Assert.assertTrue(foundReport2);
        Assert.assertTrue(list.size() == 2);
    }
}
