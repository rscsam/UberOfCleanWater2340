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
import a2340.uberofcleanwater.model.PurityReport;


/**
 * Adapter for recycler view displaying purity reports
 *
 * @author Anna Bergstrom
 * @version 1.0
 * @since 2017-03-17
 */

public class PurityReportAdapter extends RecyclerView.Adapter<PurityReportAdapter.ViewHolder> {
    private final List<PurityReport> mDataSet;

    /**
     * A ViewHolder that encapsulates all the information contained in each RecyclerView item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView coordsTV;
        final TextView dateTV;
        final TextView reportNumTV;
        final TextView authorTV;
        final TextView conditionTV;
        final TextView virus_ppmTV;
        final TextView contaminant_ppmTV;

        ViewHolder(View v) {
            super(v);
            coordsTV = (TextView) v.findViewById(R.id.coords_tv);
            dateTV = (TextView) v.findViewById(R.id.date_tv);
            reportNumTV = (TextView) v.findViewById(R.id.report_num_tv);
            authorTV = (TextView) v.findViewById(R.id.author_tv);
            conditionTV = (TextView) v.findViewById(R.id.purity_condition_tv);
            virus_ppmTV = (TextView) v.findViewById(R.id.virus_ppm_tv);
            contaminant_ppmTV = (TextView) v.findViewById(R.id.contaminant_ppm_tv);
        }
    }

    /**
     * A constructor for the adapter that initializes the data set with given WaterReports
     *
     * @param myDataSet  the waterReports being viewed
     */
    public PurityReportAdapter(List<PurityReport> myDataSet) {
        mDataSet = myDataSet;
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
        PurityReport pr = mDataSet.get(position);
        holder.coordsTV.setText("(" + pr.getLatitude() + ", " + pr.getLongitude() + ")");
        String dateText = pr.getDate().toString().substring(4, 10) + pr.getDate().toString().substring(23);
        holder.dateTV.setText(dateText);
        holder.authorTV.setText(pr.getAuthor());

        String condition = pr.getCondition().toString();
        holder.conditionTV.setText(condition); //null pointer?

        String contaminantString = "" + pr.getContaminantPPM();
        holder.contaminant_ppmTV.setText(contaminantString);
        String virusString = "" + pr.getVirusPPM();
        holder.virus_ppmTV.setText(virusString);
        String reportNumString = "" + pr.getReportNum();
        holder.reportNumTV.setText(reportNumString);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
