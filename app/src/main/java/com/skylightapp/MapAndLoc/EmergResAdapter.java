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
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EmergResAdapter extends RecyclerView.Adapter<EmergResAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<EmergResponse> responseArrayList;
    private List<EmergResponse> emergResponseList = new ArrayList<>();
    private List<EmergResponse> itemsListFilter = new ArrayList<>();
    private Context mcontext;
    int resources,emergID,deviceID;
    private OnResponseClickListener listener;
    String startTime,endTime,date,status;

    public EmergResAdapter(ArrayList<EmergResponse> recyclerDataArrayList, Context mcontext) {
        this.responseArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public EmergResAdapter(Context context, int resources, ArrayList<EmergResponse> responseArrayList) {
        this.responseArrayList = responseArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public EmergResAdapter(Context context, ArrayList<EmergResponse> responseArrayList) {
        this.responseArrayList = responseArrayList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                emergResponseList = (List<EmergResponse>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<EmergResponse> filteredList = new ArrayList<>();
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

                for (EmergResponse emergResponse : itemsListFilter) {
                    if (emergResponse.getResponseDate().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(emergResponse);
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
            itemsListFilter.addAll(responseArrayList);
        } else {
            for (EmergResponse ls : responseArrayList) {

                if (ls.getResponseDate().contains(pack)) {

                    itemsListFilter.add(ls);

                }

            }
            notifyDataSetChanged();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView reportId, txtEmergID;
        public final BezelImageView userPicture;
        public TextView txtEmergDate, txtEmergEndDate;
        public TextView txtStartTime,status;
        private  EmergResponse emergResponse;
        private OnResponseClickListener listener;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmergID = itemView.findViewById(R.id.EmerID);
            txtEmergDate = itemView.findViewById(R.id.EmerR_Date);
            txtStartTime = itemView.findViewById(R.id.EmerR_Time);
            txtEmergEndDate = itemView.findViewById(R.id.EmerR_EndDate);
            status = itemView.findViewById(R.id.EmerR_Status);
            userPicture = itemView.findViewById(R.id.user_pix11);
        }
        @Override
        public void onClick(View view) {
            if(listener!=null)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION) {
                    listener.onResponseClick(emergResponse);
                    listener.onResponsePositionClick(position);
                }

            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EmergResAdapter.RecyclerViewHolder holder, int position) {
        EmergResponse emergResponse = responseArrayList.get(position);
        if(emergResponse !=null){
            emergID=emergResponse.getResponseEmergID();
            startTime=emergResponse.getResponseStartTime();
            endTime=emergResponse.getResponseEndTime();
            status=emergResponse.getResponseStatus();
            deviceID=emergResponse.getResponseDeviceID();
            date=emergResponse.getResponseDate();

        }
        holder.txtEmergID.setText(MessageFormat.format("ID{0}", emergID+"/"+deviceID));
        holder.txtEmergDate.setText(MessageFormat.format("Date{0}", date));
        holder.txtStartTime.setText(MessageFormat.format("Start Time{0}", startTime));
        holder.txtEmergEndDate.setText(MessageFormat.format("End Time{0}", endTime));
        holder.status.setText(MessageFormat.format("Status{0}", status));
    }

    @Override
    public int getItemCount() {
        return (null != responseArrayList ? responseArrayList.size() : 0);
    }
    public interface OnResponseClickListener{
        void onResponseClick(EmergResponse emergResponse);
        void onResponsePositionClick(int responsePosition);
    }
}
