package a2340.uberofcleanwater.controller;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.controller.adapter.MapMarkerAdapter;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.ReportList;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Activity which shows pins on the map
 *
 * @author Ryan Anderson
 * @author Sylvia Necula
 * @version 1.0
 * @since 2017-02-28
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Adds markers to the map where water reports are located. When markers clicked, can see info
     * about the report.
     *
     * @param googleMap the google map being used
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<WaterReport> reports = ReportList.getReportList(db);
        for (WaterReport r : reports) {
            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
            //pass all info to be displayed in title field of marker
            googleMap.addMarker(new MarkerOptions().position(loc).title(Integer.toString(r.getReportNum())
                    + "_" + r.getType().toString() + "_" + r.getCondition().toString() + "_" + r.getAuthor()
                    + "_" + Double.toString(r.getLatitude()) + "_" + Double.toString(r.getLongitude())));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
        googleMap.setInfoWindowAdapter(new MapMarkerAdapter(getLayoutInflater()));
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
