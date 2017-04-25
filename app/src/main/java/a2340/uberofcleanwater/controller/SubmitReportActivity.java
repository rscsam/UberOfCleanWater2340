package a2340.uberofcleanwater.controller;


import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private final int MY_ACCESS_FINE_LOCATION_REQUEST = 1;
    private Location loc;

    /**
     * Location listener used to update the location from the GPS.
     */
    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLoc(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_item, WaterReport.legalConditions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_item, WaterReport.legalTypes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);

        ArrayAdapter<String> latitudeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, WaterReport.latitudeHemispheres);
        latitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitudeSpinner.setAdapter(latitudeAdapter);

        ArrayAdapter<String> longitudeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, WaterReport.longitudeHemispheres);
        longitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudeSpinner.setAdapter(longitudeAdapter);

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this,
                        "Location permission need to auto-generate Latitude and Longitude.",
                        Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_ACCESS_FINE_LOCATION_REQUEST);
            }
        } else {
            setLatLong();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_ACCESS_FINE_LOCATION_REQUEST) {
            if ((grantResults.length > 0) && (grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)) {
                setLatLong();
            }
        }
    }

    private void setLatLong () {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            EditText latitudeET = (EditText) findViewById(R.id.latitude_in);
            EditText longitudeET = (EditText) findViewById(R.id.longitude_in);
            Spinner latitudeSP = (Spinner) findViewById(R.id.latitude_spinner);
            Spinner longitudeSP = (Spinner) findViewById(R.id.longitude_spinner);
            if (loc != null) {
                Double lat = loc.getLatitude();
                Double lng = loc.getLongitude();
                if (lat < 0) {
                    latitudeSP.setSelection(WaterReport.latitudeHemispheres.indexOf("South"));
                    lat *= -1;
                }
                if (lng > 0) {
                    longitudeSP.setSelection(WaterReport.longitudeHemispheres.indexOf("East"));
                } else {
                    lng *= -1;
                }
                String latText = lat.toString();
                String lngText = lng.toString();
                latitudeET.setText(latText);
                longitudeET.setText(lngText);
            } else {
                Toast.makeText(this, "Location could not be auto-generated. Is GPS enabled?",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Helper class which sets the location to most recently found location.
     * @param location - Location received by the LocationListener.
     */
    private  void setLoc(Location location) {
        loc = location;
    }

    /**
     * saves all the changes to the user object and then exits the activity
     * @param view the button clicked
     */
    public void onSubmitPressed(View view) {
        final int MAX_LAT = 90;
        final int MAX_LONG = 180;

        final String nameET = username;
        final String latitudeET = ((EditText) findViewById(R.id.latitude_in)).getText().toString();
        final String longitudeET = ((EditText) findViewById(
                R.id.longitude_in)).getText().toString();
        final String waterTypeET = (String) ((Spinner) findViewById(
                R.id.water_type_in)).getSelectedItem();
        final String waterConditionET = (String) ((Spinner) findViewById(
                R.id.water_condition_in)).getSelectedItem();
        final String latHemisphere = (String) ((Spinner) findViewById(
                R.id.latitude_spinner)).getSelectedItem();
        final String longHemisphere = (String) ((Spinner) findViewById(
                R.id.longitude_spinner)).getSelectedItem();

        if (latitudeET.isEmpty() || longitudeET.isEmpty()) {
            Toast.makeText(this, "Latitude or Longitude is blank", Toast.LENGTH_LONG).show();
        } else {
            try {
                Double lat = Double.parseDouble(latitudeET);
                Double longitude = Double.parseDouble(longitudeET);

                if ((lat < 0) || (lat > MAX_LAT) || (longitude < 0) || (longitude > MAX_LONG)) {
                    Toast.makeText(this, "Longitude or Latitude is an invalid number",
                            Toast.LENGTH_LONG).show();
                } else {
                    if ("South".equals(latHemisphere)) {
                        lat *= -1;
                    }
                    if ("West".equals(longHemisphere)) {
                        longitude *= -1;
                    }
                    WaterReport waterReport = new WaterReport(nameET, longitude, lat,
                            WaterReport.stringToWT(waterTypeET),
                            WaterReport.stringToWC(waterConditionET));
                    ReportList.addReport(db, waterReport);
                    Toast.makeText(this, "Submission Successful", Toast.LENGTH_LONG).show();
                    finish();
                }

            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Latitude or Longitude is not a number",
                        Toast.LENGTH_LONG).show();
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
