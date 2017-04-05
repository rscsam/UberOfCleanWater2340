package a2340.uberofcleanwater.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Serves as an adapter for the view reports recycler view
 *
 * @author Sam Costley
 * @version 1.0
 * @since 2017-03-01
 */

public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.ViewHolder> {
    private final List<WaterReport> mDataSet;

    /**
     * A ViewHolder that encapsulates all the information contained in each RecyclerView item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView coordsTV;
        final TextView dateTV;
        final TextView reportNumTV;
        final TextView authorTV;
        final TextView waterTypeTV;
        final TextView conditionTV;

        ViewHolder(View v) {
            super(v);
            coordsTV = (TextView) v.findViewById(R.id.coords_tv);
            dateTV = (TextView) v.findViewById(R.id.date_tv);
            reportNumTV = (TextView) v.findViewById(R.id.report_num_tv);
            authorTV = (TextView) v.findViewById(R.id.author_tv);
            waterTypeTV = (TextView) v.findViewById(R.id.water_type_tv);
            conditionTV = (TextView) v.findViewById(R.id.water_condition_tv);
        }
    }

    /**
     * A constructor for the adapter that initializes the data set with given WaterReports
     *
     * @param myDataSet  the waterReports being viewed
     */
    public WaterReportAdapter(List<WaterReport> myDataSet) {
        mDataSet = myDataSet;
    }

    @Override
    public WaterReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View waterReportView = li.inflate(R.layout.recyclerview_water_reports, parent, false);
        return new ViewHolder(waterReportView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WaterReport wr = mDataSet.get(position);
        holder.coordsTV.setText("(" + wr.getLatitude() + ", " + wr.getLongitude() + ")");
        String dateText = wr.getDate().toString().substring(4, 10) + wr.getDate().toString().substring(23);
        holder.dateTV.setText(dateText);
        holder.authorTV.setText(wr.getAuthor());

        String condition = wr.getCondition().toString();
        if ("TreatableClear".equals(condition)) {
            condition = "Treatable/Clear";
        }
        else if ("TreatableMuddy".equals(condition)) {
            condition = "Treatable/Muddy";
        }
        holder.conditionTV.setText(condition);

        holder.waterTypeTV.setText(wr.getType().toString());
        String reportNumString = "" + wr.getReportNum();
        holder.reportNumTV.setText(reportNumString);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}