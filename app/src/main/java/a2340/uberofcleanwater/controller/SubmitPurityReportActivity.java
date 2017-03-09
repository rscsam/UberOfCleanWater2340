package a2340.uberofcleanwater.controller;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.PurityCondition;
import a2340.uberofcleanwater.model.PurityReport;
import a2340.uberofcleanwater.model.PurityReportList;

/**
 * Activity class to handle input from the Submit Purity Report Screen.
 * @author Seth Triplett
 * @version 1.0
 * @since 2017-03-08
 */

public class SubmitPurityReportActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_report);
        setTitle("Submit Purity Report");
        username = getIntent().getStringExtra("username");

        Spinner conditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, PurityReport.legalConditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    /**
     * When the submit button is pressed, save all data and exit the screen.
     * @param view - the submit button pressed.
     */
    public void onSubmitPressed(View view) {
        final String nameString = username;
        final Spinner conditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);
        final EditText contaminantET = (EditText) findViewById(R.id.contaminantppm_et);
        final EditText virusET = (EditText) findViewById(R.id.virusppm_et);
        final EditText latitudeET = (EditText) findViewById(R.id.latitude_et);
        final EditText longitudeET = (EditText) findViewById(R.id.longitude_et);


       String condition = conditionSpinner.getSelectedItem().toString();
        int contaminant = -1;
        int virus = -1;
        double latitude = -1;
        double longitude = -1;
        boolean success = true;

        if (contaminantET.getText().toString().isEmpty()) {
            Toast.makeText(this, "ContaminantPPM is required.", Toast.LENGTH_LONG).show();
            success = false;
        } else if (virusET.getText().toString().isEmpty()) {
            Toast.makeText(this, "VirusPPM is required.", Toast.LENGTH_LONG).show();
            success = false;
        } else if (latitudeET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Latitude is required.", Toast.LENGTH_LONG).show();
            success = false;
        } else if (longitudeET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Longitude is required.", Toast.LENGTH_LONG).show();
            success = false;
        }
         try {
            contaminant = Integer.parseInt(contaminantET.getText().toString());
        } catch (NumberFormatException e) {
            if (success) Toast.makeText(this, "ContaminantPPM is not an integer", Toast.LENGTH_LONG).show();
            success = false;
        }
        try {
            virus = Integer.parseInt(virusET.getText().toString());
        } catch (NumberFormatException e) {
            if (success) Toast.makeText(this, "VirusPPM is not an integer", Toast.LENGTH_LONG).show();
            success = false;
        }
         try {
            latitude = Double.parseDouble(latitudeET.getText().toString());
        } catch (NumberFormatException e) {
            if (success) Toast.makeText(this, "Latitude is not a number", Toast.LENGTH_LONG).show();
             success = false;
        }
        try {
            longitude = Double.parseDouble(longitudeET.getText().toString());
        } catch (NumberFormatException e) {
            if (success) Toast.makeText(this, "Longitude is not a number", Toast.LENGTH_LONG).show();
            success = false;
        }
        if (latitude < -90 || latitude > 90) {
            if (success) Toast.makeText(this, "Latitude is not in the range -90 to 90", Toast.LENGTH_LONG).show();
            success = false;
        }
        else if (longitude < -180 || longitude > 180) {
            if (success) Toast.makeText(this, "Longitude is not in the range -180 to 180", Toast.LENGTH_LONG).show();
            success = false;
        } else {
            PurityReport purityReport = new PurityReport(nameString, longitude, latitude, PurityCondition.valueOf(condition), contaminant, virus);
            PurityReportList.addReport(db, purityReport);
            Toast.makeText(this, "Purity Report successfully submitted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Cancels the activity without saving any data.
     * @param view - The cancel button
     */
    public void onCancelPressed(View view) {
        finish();
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
