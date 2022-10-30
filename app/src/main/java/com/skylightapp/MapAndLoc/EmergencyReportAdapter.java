package com.skylightapp.MapAndLoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Markets.MarketDetailsAct;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class EmergencyReportAdapter extends RecyclerView.Adapter<EmergencyReportAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<EmergencyReport> emergencyReports;
    private List<EmergencyReport> theSlideItemsModelClassList = new ArrayList<>();
    private List<EmergencyReport> itemsListFilter = new ArrayList<>();
    private Context mcontext;
    int resources;
    private OnEmergClickListener listener;

    public EmergencyReportAdapter(ArrayList<EmergencyReport> recyclerDataArrayList, Context mcontext) {
        this.emergencyReports = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public EmergencyReportAdapter(Context context, int resources, ArrayList<EmergencyReport> emergencyReports) {
        this.emergencyReports = emergencyReports;
        this.mcontext = context;
        this.resources = resources;

    }

    public EmergencyReportAdapter(Context context,  ArrayList<EmergencyReport> emergencyReports) {
        this.emergencyReports = emergencyReports;
        this.mcontext = context;

    }

    public EmergencyReportAdapter(MarketDetailsAct marketDetailsAct, ArrayList<EmergencyReport> emergencyReports) {
        this.emergencyReports = emergencyReports;
        this.mcontext = marketDetailsAct;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                theSlideItemsModelClassList = (List<EmergencyReport>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<EmergencyReport> filteredList = new ArrayList<>();
                String searchText = constraint.toString().toLowerCase();
                String[] split = searchText.split(",");
                ArrayList<String> stringArrayList = new ArrayList<>(split.length);
                for (String aSplit : split) {
                    // remove spaces
                    String trim = aSplit.trim();
                    // skip empty entries
                    if (trim.length() > 0)
                        stringArrayList.add(trim);
                }

                for (EmergencyReport dataNames : itemsListFilter) {
                    if (dataNames.getEmergRType().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
                return results;
            }

        };
        return filter;
    }
    public void filterAll( String pack ) {

        itemsListFilter.clear();

        if (pack.length() < 0) {
            itemsListFilter.addAll(emergencyReports);
        } else {
            for (EmergencyReport ls : emergencyReports) {

                if (ls.getEmergRType().contains(pack)) {

                    itemsListFilter.add(ls);

                }

            }
            notifyDataSetChanged();
        }
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView reportId,profileID;
        public final BezelImageView userPicture;
        public TextView reportType,latLng;
        public TextView timeStamp,status;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            profileID = itemView.findViewById(R.id.EmergProfileID);
            reportType = itemView.findViewById(R.id.EmergType);
            timeStamp = itemView.findViewById(R.id.Emerg_date);
            latLng = itemView.findViewById(R.id.EmergLatLng);
            status = itemView.findViewById(R.id.Emerg_StatusTxt);
            userPicture = itemView.findViewById(R.id.user_pix11);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        EmergencyReport recyclerData = emergencyReports.get(position);
        holder.profileID.setText(MessageFormat.format("ID{0}", recyclerData.getEmergReportID()+""+"ProfileID"+recyclerData.getProfileID()));
        holder.reportType.setText(MessageFormat.format("Type{0}", recyclerData.getEmergRType()));
        holder.timeStamp.setText(MessageFormat.format("Time Stamp{0}", String.valueOf(recyclerData.getEmergRTime())));
        holder.latLng.setText(MessageFormat.format("Location{0}", recyclerData.getEmergRLatLng()));
        holder.status.setText(MessageFormat.format("Status{0}", recyclerData.getEmergRStatus()));
    }

    @Override
    public int getItemCount() {
        return (null != emergencyReports ? emergencyReports.size() : 0);
    }
    public interface OnEmergClickListener {
        void onItemClick(EmergencyReport emergencyReport);
    }
}
