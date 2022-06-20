package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.Classes.ProfileManager;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TellerReportAdapter extends RecyclerView.Adapter<TellerReportAdapter.RecyclerViewHolder> implements Filterable {


    private ArrayList<TellerReport> savings;
    private Context mcontext;
    int resources;
    private List<TellerReport> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static ProfileManager profileManager;
    private Context context;

    private List<TellerReport> mSliderItems = new ArrayList<>();
    private Context Mcontext;
    private List<TellerReport> theSlideItemsModelClassList = new ArrayList<>();
    private List<TellerReport> itemsListFilter = new ArrayList<>();
    int position;
    private OnItemsClickListener listener;

    public TellerReportAdapter(ArrayList<TellerReport> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public TellerReportAdapter(View itemView, final Callback callback) {
        super();

        this.callback = callback;
        this.context = itemView.getContext();

        if (callback != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (position != RecyclerView.NO_POSITION) {
                        callback.onLongItemClick(v, position);
                        return true;
                    }

                    return false;
                }
            });
        }
    }

    public TellerReportAdapter(Context context, int resources, ArrayList<TellerReport> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;
        this.resources = resources;

    }

    public TellerReportAdapter(Context context, ArrayList<TellerReport> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teller_report_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        //SavingsAdapter.bindData(getItemByPosition(position));
        TellerReport tellerReport = this.savings.get(position);
        holder.tellerReportID.setText(MessageFormat.format("Teller Report ID:{0}", String.valueOf(tellerReport.getTellerReportID())));
        holder.tellerReportDate.setText(MessageFormat.format("Report date:{0}", tellerReport.getTellerReportDate()));
        holder.reportAmountTendered.setText(MessageFormat.format("Report Cash Amount: NGN{0}", String.format("%.2f", tellerReport.getAmtSubmitted())));
        holder.expectedAmount.setText(MessageFormat.format("Total: NGN{0}", String.format("%.2f", tellerReport.getAmtExpected())));
        holder.amountPaidOnline.setText(MessageFormat.format("Amount Paid:NGN{0}", String.valueOf(tellerReport.getAmtPaid())));
        holder.balance.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", tellerReport.getBalance())));
        holder.tellerMarketer.setText(MessageFormat.format("Teller:{0}", tellerReport.getReportMarketer()));
        holder.status.setText(MessageFormat.format("Status:{0}", tellerReport.getReportStatus()));


        if (tellerReport.getReportStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.tellerMarketer.setTextColor(Color.MAGENTA);
        } else if (tellerReport.getReportStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.tellerMarketer.setTextColor(Color.RED);
        } else if (tellerReport.getReportStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.tellerMarketer.setTextColor(Color.RED);
        }


    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                theSlideItemsModelClassList = (List<TellerReport>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<TellerReport> filteredList = new ArrayList<>();
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

                for (TellerReport dataNames : itemsListFilter) {
                    // filter by title
                    if (dataNames.getReportMarketer().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                    if (dataNames.getReportStatus().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                    if (dataNames.getReport_Office_Branch().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                    if (dataNames.getAdminName().toLowerCase().trim().contains(searchText)) {
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

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView tellerMarketer, savingsCount, tellerReportDate, status, balance, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView  expectedAmount, amountPaidOnline, tellerReportID;
        public AppCompatButton btnMore;
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.report_icon);
            tellerReportID = convertView.findViewById(R.id.tellerReportID);
            tellerMarketer = convertView.findViewById(R.id.tellerMarketer);
            tellerReportDate = convertView.findViewById(R.id.tellerReportDate);
            reportAmountTendered = convertView.findViewById(R.id.reportAmountTendered);
            amountPaidOnline = convertView.findViewById(R.id.amountPaidOnline);
            expectedAmount = convertView.findViewById(R.id.expectedAmount);
            balance = convertView.findViewById(R.id.balance);
            status = convertView.findViewById(R.id.reportStatus);

        }
    }


    @Override
    public int getItemCount() {
        return (null != savings ? savings.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onPackageClick(String packageId, View view);
        void onAuthorClick(String authorId, View view);
    }


    public TellerReport getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<TellerReport> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(TellerReport tellerReport, String officeBranch);
    }

}
