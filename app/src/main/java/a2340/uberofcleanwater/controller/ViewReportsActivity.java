package a2340.uberofcleanwater.controller;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.controller.adapter.WaterReportAdapter;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Activity which displays water reports
 *
 * @author Sam Costley
 * @version 1.0
 * @since 2017-02-20
 */
public class ViewReportsActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        ArrayList<WaterReport> reports = ReportList.getReportList(db);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.report_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RecyclerView.Adapter mAdapter = new WaterReportAdapter(reports);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * cancels the activity
     * @param view the button pressed
     */
    public void onCancelPressed(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
