package a2340.uberofcleanwater.controller;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Creates a new water report
 *
 * @author Anna Bergstrom
 * @author Sylvia Necula
 * @version 1.0
 * @since 2017-02-28
 */

public class SubmitReportActivity extends AppCompatActivity{

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        setTitle("Submit Report");
        username = getIntent().getStringExtra("username");

        Spinner conditionSpinner = (Spinner) findViewById(R.id.waterCondition_in);
        Spinner typeSpinner = (Spinner) findViewById(R.id.waterType_in);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterReport.legalConditions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterReport.legalTypes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);

    }



    /**
     * saves all the changes to the user object and then exits the activity
     * @param view the button clicked
     */
    public void onSubmitPressed(View view) {
        final String nameET = username;
        final String latitudeET = ((EditText) findViewById(R.id.latitude_in)).getText().toString();
        final String longitudeET = ((EditText) findViewById(R.id.longitude_in)).getText().toString();
        final String waterTypeET = (String) ((Spinner) findViewById(R.id.waterType_in)).getSelectedItem();
        final String waterConditionET = (String) ((Spinner) findViewById(R.id.waterCondition_in)).getSelectedItem();
        WaterReport waterReport = new WaterReport(nameET, Double.parseDouble(latitudeET), Double.parseDouble(longitudeET), WaterReport.stringToWT(waterTypeET), WaterReport.stringToWC(waterConditionET));
        ReportList.addReport(waterReport);
        finish();
    }

    /**
     * cancels the activity
     * @param view the button pressed
     */
    public void onCancelPressed(View view) {
        finish();
    }
}
