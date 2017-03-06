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
 * Created by sam on 3/1/17.
 */

public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.ViewHolder> {
    private ArrayList<WaterReport> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView coordsTV;
        public TextView dateTV;
        public TextView reportNumTV;
        public TextView authorTV;
        public TextView waterTypeTV;
        public TextView conditionTV;

        public ViewHolder(View v) {
            super(v);
            coordsTV = (TextView) v.findViewById(R.id.coords_tv);
            dateTV = (TextView) v.findViewById(R.id.date_tv);
            reportNumTV = (TextView) v.findViewById(R.id.report_num_tv);
            authorTV = (TextView) v.findViewById(R.id.author_tv);
            waterTypeTV = (TextView) v.findViewById(R.id.water_type_tv);
            conditionTV = (TextView) v.findViewById(R.id.water_condition_tv);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WaterReportAdapter(ArrayList<WaterReport> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WaterReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View waterReportView = li.inflate(R.layout.recyclerview_water_reports, parent, false);
        return new ViewHolder(waterReportView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        WaterReport wr = mDataset.get(position);
        holder.coordsTV.setText("(" + wr.getLatitude() + ", " + wr.getLongitude() + ") ");
        holder.dateTV.setText(wr.getDate().toString() + " ");
        holder.authorTV.setText(wr.getAuthor());
        holder.conditionTV.setText(wr.getCondition().toString() + " ");
        holder.waterTypeTV.setText(wr.getType().toString() + " ");
        holder.reportNumTV.setText("" + wr.getReportNum() + " ");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}