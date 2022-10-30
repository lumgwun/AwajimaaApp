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

import com.skylightapp.Markets.MarketDetailsAct;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EmergReportMapA extends RecyclerView.Adapter<EmergReportMapA.RecyclerViewHolder> implements Filterable {

    private ArrayList<EmergencyReport> emergencyReports;
    private List<EmergencyReport> theSlideItemsModelClassList = new ArrayList<>();
    private List<EmergencyReport> itemsListFilter = new ArrayList<>();
    private Context mcontext;
    int resources;
    private EmergencyReportAdapter.OnEmergClickListener listener;

    public EmergReportMapA(ArrayList<EmergencyReport> recyclerDataArrayList, Context mcontext) {
        this.emergencyReports = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public EmergReportMapA(Context context, int resources, ArrayList<EmergencyReport> emergencyReports) {
        this.emergencyReports = emergencyReports;
        this.mcontext = context;
        this.resources = resources;

    }

    public EmergReportMapA(MarketDetailsAct marketDetailsAct, ArrayList<EmergencyReport> emergencyReports) {
        this.emergencyReports = emergencyReports;
        this.mcontext = marketDetailsAct;
    }

    @NonNull
    @Override
    public EmergReportMapA.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lat_lng_row, parent, false);
        return new EmergReportMapA.RecyclerViewHolder(view);
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

        public TextView reportId, mapLat;
        public TextView mapLng,latLng;
        public TextView timeStamp,status;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mapLat = itemView.findViewById(R.id.ELat);
            mapLng = itemView.findViewById(R.id.ELng);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EmergReportMapA.RecyclerViewHolder holder, int position) {
        EmergencyReport recyclerData = emergencyReports.get(position);
        holder.mapLat.setText(MessageFormat.format("Lat{0}", recyclerData.getEmergRLat()));
        holder.mapLng.setText(MessageFormat.format("Lng{0}", recyclerData.getEmergRLng()));
    }

    @Override
    public int getItemCount() {
        return (null != emergencyReports ? emergencyReports.size() : 0);
    }
    public interface OnItemsClickListener{
        void onItemClick(EmergencyReport emergencyReport);
    }
}
