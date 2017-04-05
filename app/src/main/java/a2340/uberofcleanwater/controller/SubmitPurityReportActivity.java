package a2340.uberofcleanwater.controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
 * @author Sam Costley
 * @version 1.1
 * @since 2017-03-08
 */

public class SubmitPurityReportActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private String username;

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
        setContentView(R.layout.activity_purity_report);
        setTitle("Submit Purity Report");
        username = getIntent().getStringExtra("username");

        Spinner conditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);
        Spinner latitudeSpinner = (Spinner) findViewById(R.id.lat_spinner);
        Spinner longitudeSpinner = (Spinner) findViewById(R.id.longitude_spinner);

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PurityReport.legalConditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        ArrayAdapter<String> latitudeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PurityReport.latitudeHemispheres);
        latitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitudeSpinner.setAdapter(latitudeAdapter);

        ArrayAdapter<String> longitudeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PurityReport.longitudeHemispheres);
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
            EditText latitudeET = (EditText) findViewById(R.id.latitude_et);
            EditText longitudeET = (EditText) findViewById(R.id.longitude_et);
            Spinner latitudeSP = (Spinner) findViewById(R.id.lat_spinner);
            Spinner longitudeSP = (Spinner) findViewById(R.id.longitude_spinner);
            if (loc != null) {
                Double lat = loc.getLatitude();
                Double lng = loc.getLongitude();
                if (lat < 0) {
                    latitudeSP.setSelection(PurityReport.latitudeHemispheres.indexOf("South"));
                    lat *= -1;
                }
                if (lng > 0) {
                    longitudeSP.setSelection(PurityReport.longitudeHemispheres.indexOf("East"));
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
     * When the submit button is pressed, save all data and exit the screen.
     * @param view - the submit button pressed.
     */
    public void onSubmitPressed(View view) {
        final String nameString = username;
        final Spinner conditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);
        final EditText contaminantET = (EditText) findViewById(R.id.contaminant_ppm_et);
        final EditText virusET = (EditText) findViewById(R.id.virus_ppm_et);
        final EditText latitudeET = (EditText) findViewById(R.id.latitude_et);
        final EditText longitudeET = (EditText) findViewById(R.id.longitude_et);
        final Spinner latitudeSpinner = (Spinner) findViewById(R.id.lat_spinner);
        final Spinner longitudeSpinner = (Spinner) findViewById(R.id.longitude_spinner);


       String condition = conditionSpinner.getSelectedItem().toString();
        int contaminant;
        int virus;
        double latitude;
        double longitude;
        final int MIN_LAT = -90;
        final int MAX_LAT = 90;
        final int MIN_LONG = -180;
        final int MAX_LONG = 180;

        if (contaminantET.getText().toString().isEmpty()) {
            Toast.makeText(this, "ContaminantPPM is required.", Toast.LENGTH_LONG).show();
            return;
        } else if (virusET.getText().toString().isEmpty()) {
            Toast.makeText(this, "VirusPPM is required.", Toast.LENGTH_LONG).show();
            return;
        } else if (latitudeET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Latitude is required.", Toast.LENGTH_LONG).show();
            return;
        } else if (longitudeET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Longitude is required.", Toast.LENGTH_LONG).show();
            return;
        }
         try {
            contaminant = Integer.parseInt(contaminantET.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ContaminantPPM is not an integer", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            virus = Integer.parseInt(virusET.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "VirusPPM is not an integer", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            latitude = Double.parseDouble(latitudeET.getText().toString());
            if (latitudeSpinner.getSelectedItem().toString().equals(
                    PurityReport.latitudeHemispheres.get(1))) {
                latitude *= -1;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude is not a number", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            longitude = Double.parseDouble(longitudeET.getText().toString());
            if (longitudeSpinner.getSelectedItem().toString().equals(
                    PurityReport.longitudeHemispheres.get(0))) {
                longitude *= -1;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Longitude is not a number", Toast.LENGTH_LONG).show();
            return;
        }
        if ((latitude < MIN_LAT) || (latitude > MAX_LAT)) {
            Toast.makeText(this, "Latitude is not in the range -90 to 90",
                    Toast.LENGTH_LONG).show();
        }
        else if ((longitude < MIN_LONG) || (longitude > MAX_LONG)) {
            Toast.makeText(this, "Longitude is not in the range -180 to 180",
                    Toast.LENGTH_LONG).show();
        } else {
            PurityReport purityReport = new PurityReport(nameString, longitude,
                    latitude, PurityCondition.valueOf(condition), contaminant, virus);
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
