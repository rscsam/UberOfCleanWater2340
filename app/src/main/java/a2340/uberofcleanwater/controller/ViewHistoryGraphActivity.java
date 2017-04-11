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

import java.util.ArrayList;
import java.util.List;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.PurityReport;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterReport;


/**
 * Activity which handles the history graph viewing and drawing
 *
 * @author Sam Costley
 * @version 1.0
 * @since 2017-04-01
 */

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

        ArrayAdapter<String> latitudeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, WaterReport.latitudeHemispheres);
        latitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitudeSpinner.setAdapter(latitudeAdapter);

        ArrayAdapter<String> longitudeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, WaterReport.longitudeHemispheres);
        longitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudeSpinner.setAdapter(longitudeAdapter);
    }

    /**
     * Onclick method for the update button that processes current input and redraws the graph
     *
     * @param view  The Update Button
     */
    public void updateGraph(View view) {
        final int MAX_LAT = 90;
        final int MAX_LONG = 180;

        final RadioGroup typeRG = (RadioGroup) findViewById(R.id.ppm_type_rg);
        int button = typeRG.getCheckedRadioButtonId();
        View rgView = typeRG.findViewById(button);
        int selection = typeRG.indexOfChild(rgView);
        final String yearET = ((EditText) findViewById(R.id.graph_year_et)).getText().toString();
        final String proximityET = ((EditText) findViewById(
                R.id.proximity_et)).getText().toString();
        final String latitudeET = ((EditText) findViewById(
                R.id.graph_latitude_et)).getText().toString();
        final String longitudeET = ((EditText) findViewById(
                R.id.graph_longitude_et)).getText().toString();
        final String longHemisphere = (String) ((Spinner) findViewById(
                R.id.graph_longitude_spinner)).getSelectedItem();
        final String latHemisphere = (String) ((Spinner) findViewById(
                R.id.graph_latitude_spinner)).getSelectedItem();
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
            if ((lat > MAX_LAT) || (lat < 0)) {
                Toast.makeText(this, "Invalid Latitude", Toast.LENGTH_LONG).show();
            }
            if ((longitude > MAX_LONG) || (longitude < 0)) {
                Toast.makeText(this, "Invalid Longitude", Toast.LENGTH_LONG).show();
            }
            if (proximityET.isEmpty()) {
                proximity = 0;
            } else {
                try {
                    proximity = Double.parseDouble(proximityET);

                    if ("South".equals(latHemisphere)) {
                        lat *= -1;
                    }
                    if ("West".equals(longHemisphere)) {
                        longitude *= -1;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid Proximity", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        List<PurityReport> reports = ReportList.getReportsInProximity(lat, longitude, proximity,
                yearET, db);
        if (reports.isEmpty()) {
            Toast.makeText(this, "No purity reports in range.", Toast.LENGTH_LONG).show();
            return;
        }
        redrawGraph(condenseIntoMonthlyAverages(reports, selection));
    }

    private double[] condenseIntoMonthlyAverages(Iterable<PurityReport> reports, int ppmType) {
        double[] condensedSums = new double[12];
        int[] numsPerMonth = new int[12];
        for (PurityReport r : reports) {
            //int i = r.getDate().getMonth();
            int i = 1;
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
        BarChart chart = (BarChart) findViewById(R.id.history_graph_bar_chart);

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
