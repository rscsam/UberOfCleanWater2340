package a2340.uberofcleanwater.controller;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.PurityReport;
import a2340.uberofcleanwater.model.PurityReportList;
import a2340.uberofcleanwater.model.WaterReport;

public class ViewHistoryGraphActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history_graph);

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        Spinner latitudeSpinner = (Spinner) findViewById(R.id.graph_latitude_spinner);
        Spinner longitudeSpinner = (Spinner) findViewById(R.id.graph_longitude_spinner);

        ArrayAdapter<String> latitudeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterReport.latitudeHemispheres);
        latitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitudeSpinner.setAdapter(latitudeAdapter);

        ArrayAdapter<String> longitudeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterReport.longitudeHemispheres);
        longitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudeSpinner.setAdapter(longitudeAdapter);
    }

    public void updateGraph(View v) {
        final RadioGroup typeRG = (RadioGroup) findViewById(R.id.ppm_type_rg);
        int button = typeRG.getCheckedRadioButtonId();
        View rgView = typeRG.findViewById(button);
        int selection = typeRG.indexOfChild(rgView);
        final String yearET = ((EditText) findViewById(R.id.graph_year_et)).getText().toString();
        final String proximityET = ((EditText) findViewById(R.id.proximity_et)).getText().toString();
        final String latitudeET = ((EditText) findViewById(R.id.graph_latitude_et)).getText().toString();
        final String longitudeET = ((EditText) findViewById(R.id.graph_longitude_et)).getText().toString();
        final String longHemisphere = (String) ((Spinner) findViewById(R.id.graph_longitude_spinner)).getSelectedItem();
        final String latHemisphere = (String) ((Spinner) findViewById(R.id.graph_latitude_spinner)).getSelectedItem();
        double lat;
        double longitude;
        double proximity;
        if (latitudeET.isEmpty() || longitudeET.isEmpty()) {
            Toast.makeText(this, "Latitude or Longitude is blank", Toast.LENGTH_LONG).show();
            return;
        } else if (yearET.isEmpty()) {
            Toast.makeText(this, "Year is blank", Toast.LENGTH_LONG).show();
            return;
        } else {
            try {
                lat = Double.parseDouble(latitudeET);
                longitude = Double.parseDouble(longitudeET);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid Latitude or Longitude", Toast.LENGTH_LONG).show();
                return;
            }
            if (proximityET.isEmpty()) {
                proximity = 0;
            } else {
                try {
                    proximity = Double.parseDouble(proximityET);

                    if (latHemisphere.equals("South")) {
                        lat *= -1;
                    }
                    if (longHemisphere.equals("West")) {
                        longitude *= -1;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid Proximity", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        ArrayList<PurityReport> reports = reportsInProximity(lat, longitude, proximity);
        if (reports.isEmpty()) {
            return;
        }
        redrawGraph(condenseIntoMonthlyAverages(reports, selection));
    }

    private ArrayList<PurityReport> reportsInProximity(double lat, double longitude, double proximity) {
        ArrayList<PurityReport> allReports = PurityReportList.getReportList(db);
        ArrayList<PurityReport> reports = new ArrayList<>();

        double minLat = lat - proximity;
        double maxLat = lat + proximity;
        double minLong = longitude - proximity;
        double maxLong = longitude + proximity;

        for (PurityReport r : allReports) {
            double rlat = r.getLatitude();
            double rlong = r.getLongitude();

            if (rlat >= minLat && rlat <= maxLat) {
                if (rlong >= minLong && rlong <= maxLong) {
                    reports.add(r);
                }
            }
        }
        return reports;
    }

    private double[] condenseIntoMonthlyAverages(ArrayList<PurityReport> reports, int ppmType) {
        double[] condensedSums = new double[12];
        int[] numsPerMonth = new int[12];
        for (PurityReport r : reports) {
            int i = r.getDate().getMonth();
            numsPerMonth[i] = numsPerMonth[i] + 1;
            if (ppmType == 0) {
                condensedSums[i] = condensedSums[i] + r.getVirusPPM();
            } else {
                condensedSums[i] = condensedSums[i] + r.getContaminantPPM();
            }
        }

        for (int i = 0; i < 12; i++) {
            condensedSums[i] = condensedSums[i] / numsPerMonth[i];
        }
        return condensedSums;
    }

    private void redrawGraph(double[] ppmData) {
        BarChart chart = (BarChart) findViewById(R.id.history_graph_barchart);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (float) ppmData[i]));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Average PPM");

        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        XAxis xaxis = chart.getXAxis();
        xaxis.setLabelCount(12, true);
        xaxis.setAxisMinimum(0);
        xaxis.setAxisMaximum(11);
        chart.setDescription(null);
        chart.invalidate();
    }
}
