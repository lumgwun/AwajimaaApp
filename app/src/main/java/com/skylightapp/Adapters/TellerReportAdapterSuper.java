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
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.ProfileManager;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TellerReportAdapterSuper extends RecyclerView.Adapter<TellerReportAdapterSuper.RecyclerViewHolder> implements Filterable {


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
    int position;
    private OnItemsClickListener listener;
    private List<TellerReport> tellerReportArrayList = new ArrayList<>();
    private List<TellerReport> itemsListFilter = new ArrayList<>();

    public TellerReportAdapterSuper(ArrayList<TellerReport> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public TellerReportAdapterSuper(View itemView, final Callback callback) {
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

    public TellerReportAdapterSuper(Context context, int resources, ArrayList<TellerReport> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;
        this.resources = resources;

    }

    public TellerReportAdapterSuper(Context context, ArrayList<TellerReport> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teller_report_row_super, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        //SavingsAdapter.bindData(getItemByPosition(position));
        TellerReport tellerReport = this.savings.get(position);
        holder.admin.setText(MessageFormat.format("Report Admin:{0}", String.valueOf(tellerReport.getAdminName())));
        holder.tellerReportDate.setText(MessageFormat.format("Report date:{0}", tellerReport.getTellerReportDate()));
        holder.reportAmountTendered.setText(MessageFormat.format("Report Cash Amount: NGN{0}", String.format("%.2f", tellerReport.getAmtSubmitted())));
        holder.expectedAmount.setText(MessageFormat.format("Total: NGN{0}", String.format("%.2f", tellerReport.getAmtExpected())));
        holder.amountPaidOnline.setText(MessageFormat.format("Amount Paid:NGN{0}", String.valueOf(tellerReport.getAmtPaid())));
        holder.balance.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", tellerReport.getBalance())));
        holder.tellerMarketer.setText(MessageFormat.format("Teller:{0}", tellerReport.getReportMarketer()));
        holder.status.setText(MessageFormat.format("Status:{0}", tellerReport.getReportStatus()));
        holder.vetDate.setText(MessageFormat.format("Status:{0}", tellerReport.getTxApprovalDate()));



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
//TODO
       // holder.vetDate.;


    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tellerReportArrayList = (List<TellerReport>) results.values;
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
        private TextView  expectedAmount, amountPaidOnline, tellerReportID,admin, vetDate;
        public AppCompatButton btnMore;
        private Context context;
        private List<TellerReport> mSliderItems = new ArrayList<>();
        private Context Mcontext;
        private List<TellerReport> theSlideItemsModelClassList = new ArrayList<>();
        private List<TellerReport> itemsListFilter = new ArrayList<>();
        SwitchCompat switchCompat;
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.report_icon);
            tellerMarketer = convertView.findViewById(R.id.tellerMarketerSuper);
            tellerReportDate = convertView.findViewById(R.id.tellerReportDateSuper);
            reportAmountTendered = convertView.findViewById(R.id.reportAmountTenderedSuper);
            amountPaidOnline = convertView.findViewById(R.id.amountPaidOnlineSuper);
            expectedAmount = convertView.findViewById(R.id.expectedAmountSuper);
            balance = convertView.findViewById(R.id.balanceSuper);
            status = convertView.findViewById(R.id.reportStatus);
            switchCompat = convertView.findViewById(R.id.approvalSwitch);
            admin = convertView.findViewById(R.id.reportAdmin);
            vetDate = convertView.findViewById(R.id.adminApprovalDate);

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
        void onItemClick(TellerReport tellerReport, CustomerManager customerManager, AdminUser adminUser, Profile profile, String officeBranch);
    }

}
