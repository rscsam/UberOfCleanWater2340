package a2340.uberofcleanwater.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import a2340.uberofcleanwater.R;

/**
 * @author Sylvia Necula
 * @version 1.0
 * @since 2017-03-10
 */

public class MapMarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private View popup = null;
    private LayoutInflater inflater = null;

    /**
     * Constructor to make a new InfoWindow.
     * @param inflater - LayoutInflater of the current activity
     */
    public MapMarkerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            final ViewGroup nullParent = null;
            popup = inflater.inflate(R.layout.infowindow_popup, nullParent, false);
        }

        final String[] infoArray = marker.getTitle().split("_");

        TextView tvReportNum = (TextView) popup.findViewById(R.id.report_num);
        tvReportNum.setText(infoArray[0]);

        TextView tvReportType = (TextView) popup.findViewById(R.id.report_type);
        tvReportType.setText(infoArray[1]);

        TextView tvReportCondition = (TextView) popup.findViewById(R.id.report_condition);
        tvReportCondition.setText(infoArray[2]);

        TextView tvReportAuthor = (TextView) popup.findViewById(R.id.report_author);
        tvReportAuthor.setText(infoArray[3]);

        TextView tvReportLat = (TextView) popup.findViewById(R.id.report_lat);
        tvReportLat.setText(infoArray[4]);

        TextView tvReportLong = (TextView) popup.findViewById(R.id.report_long);
        tvReportLong.setText(infoArray[5]);

        return popup;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
