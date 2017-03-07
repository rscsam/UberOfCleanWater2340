package a2340.uberofcleanwater.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
    private ArrayList<WaterReport> mDataset;

    /**
     * A ViewHolder that encapsulates all the information contained in each RecyclerView item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coordsTV;
        TextView dateTV;
        TextView reportNumTV;
        TextView authorTV;
        TextView waterTypeTV;
        TextView conditionTV;

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
     * A constructor for the adapter that initializes the dataset with given WaterReports
     *
     * @param myDataset  the waterReports being viewed
     */
    public WaterReportAdapter(ArrayList<WaterReport> myDataset) {
        mDataset = myDataset;
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
        WaterReport wr = mDataset.get(position);
        holder.coordsTV.setText("(" + wr.getLatitude() + ", " + wr.getLongitude() + ")");
        holder.dateTV.setText(wr.getDate().toString().substring(4, 10) + wr.getDate().toString().substring(23));
        holder.authorTV.setText(wr.getAuthor());

        String condition = wr.getCondition().toString();
        if (condition.equals("TreatableClear"))
            condition = "Treatable/Clear";
        else if (condition.equals("TreatableMuddy"))
            condition = "Treatable/Muddy";
        holder.conditionTV.setText(condition);

        holder.waterTypeTV.setText(wr.getType().toString());
        holder.reportNumTV.setText("" + wr.getReportNum());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}