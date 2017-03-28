package a2340.uberofcleanwater.controller;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Creates a new water report
 *
 * @author Anna Bergstrom
 * @author Sylvia Necula
 * @author Ryan Anderson
 * @author Sam Costley
 * @version 1.0
 * @since 2017-02-28
 */

public class SubmitReportActivity extends AppCompatActivity{

    private String username;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        setTitle("Submit Report");
        username = getIntent().getStringExtra("username");

        Spinner conditionSpinner = (Spinner) findViewById(R.id.water_condition_in);
        Spinner typeSpinner = (Spinner) findViewById(R.id.water_type_in);
        Spinner latitudeSpinner = (Spinner) findViewById(R.id.latitude_spinner);
        Spinner longitudeSpinner = (Spinner) findViewById(R.id.longitude_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, WaterReport.legalConditions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, WaterReport.legalTypes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);

        ArrayAdapter<String> latitudeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterReport.latitudeHemispheres);
        latitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitudeSpinner.setAdapter(latitudeAdapter);

        ArrayAdapter<String> longitudeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterReport.longitudeHemispheres);
        longitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudeSpinner.setAdapter(longitudeAdapter);

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

    }



    /**
     * saves all the changes to the user object and then exits the activity
     * @param view the button clicked
     */
    public void onSubmitPressed(View view) {
        final String nameET = username;
        final String latitudeET = ((EditText) findViewById(R.id.latitude_in)).getText().toString();
        final String longitudeET = ((EditText) findViewById(R.id.longitude_in)).getText().toString();
        final String waterTypeET = (String) ((Spinner) findViewById(R.id.water_type_in)).getSelectedItem();
        final String waterConditionET = (String) ((Spinner) findViewById(R.id.water_condition_in)).getSelectedItem();
        final String latHemisphere = (String) ((Spinner) findViewById(R.id.latitude_spinner)).getSelectedItem();
        final String longHemisphere = (String) ((Spinner) findViewById(R.id.longitude_spinner)).getSelectedItem();

        if (latitudeET.isEmpty() || longitudeET.isEmpty()) {
            Toast.makeText(this, "Latitude or Longitude is blank", Toast.LENGTH_LONG).show();
        } else {
            try {
                Double lat = Double.parseDouble(latitudeET);
                Double longitude = Double.parseDouble(longitudeET);

                if ((lat < 0) || (lat > 90) || (longitude < 0) || (longitude > 180)) {
                    Toast.makeText(this, "Longitude or Latitude is an invalid number", Toast.LENGTH_LONG).show();
                } else {
                    if (latHemisphere.equals("South")) {
                        lat *= -1;
                    }
                    if (longHemisphere.equals("West")) {
                        longitude *= -1;
                    }
                    WaterReport waterReport = new WaterReport(nameET, longitude, lat, WaterReport.stringToWT(waterTypeET), WaterReport.stringToWC(waterConditionET));
                    ReportList.addReport(db, waterReport);
                    Toast.makeText(this, "Submission Successful", Toast.LENGTH_LONG).show();
                    finish();
                }

            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Latitude or Longitude is not a number", Toast.LENGTH_LONG).show();
            }
        }
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
