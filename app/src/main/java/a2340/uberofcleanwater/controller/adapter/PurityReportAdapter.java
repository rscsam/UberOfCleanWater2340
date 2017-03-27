package a2340.uberofcleanwater.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.PurityReport;


/**
 * Created by annabergstrom on 10/03/17.
 */

public class PurityReportAdapter extends RecyclerView.Adapter<PurityReportAdapter.ViewHolder> {
    private ArrayList<PurityReport> mDataset;

    /**
     * A ViewHolder that encapsulates all the information contained in each RecyclerView item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coordsTV;
        TextView dateTV;
        TextView reportNumTV;
        TextView authorTV;
        TextView conditionTV;
        TextView virusppmTV;
        TextView contaminantppmTV;

        ViewHolder(View v) {
            super(v);
            coordsTV = (TextView) v.findViewById(R.id.coords_tv);
            dateTV = (TextView) v.findViewById(R.id.date_tv);
            reportNumTV = (TextView) v.findViewById(R.id.report_num_tv);
            authorTV = (TextView) v.findViewById(R.id.author_tv);
            conditionTV = (TextView) v.findViewById(R.id.purity_condition_tv);
            virusppmTV = (TextView) v.findViewById(R.id.virusppm_tv);
            contaminantppmTV = (TextView) v.findViewById(R.id.contaminantppm_tv);
        }
    }

    /**
     * A constructor for the adapter that initializes the dataset with given WaterReports
     *
     * @param myDataset  the waterReports being viewed
     */
    public PurityReportAdapter(ArrayList<PurityReport> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PurityReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View purityReportView = li.inflate(R.layout.recyclerview_purity_reports, parent, false);
        return new ViewHolder(purityReportView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PurityReport pr = mDataset.get(position);
        holder.coordsTV.setText("(" + pr.getLatitude() + ", " + pr.getLongitude() + ")");
        holder.dateTV.setText(pr.getDate().toString().substring(4, 10) + pr.getDate().toString().substring(23));
        holder.authorTV.setText(pr.getAuthor());

        String condition = pr.getCondition().toString();
        holder.conditionTV.setText(condition); //null pointer?

        holder.contaminantppmTV.setText(Integer.toString(pr.getContaminantPPM()));
        holder.virusppmTV.setText(Integer.toString(pr.getVirusPPM()));
        holder.reportNumTV.setText("" + pr.getReportNum());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
