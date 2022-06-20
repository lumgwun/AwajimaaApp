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

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ADepositAdapter extends RecyclerView.Adapter<ADepositAdapter.RecyclerViewHolder> implements View.OnClickListener, Filterable {


    private ArrayList<AdminBankDeposit> savings;
    private Context mcontext;
    int resources;
    private List<AdminBankDeposit> list = new ArrayList<>();
    private Callback callback;
    private List<AdminBankDeposit> adminBankDeposits;
    private List<AdminBankDeposit> bankDeposits;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static AdminUser adminUser;
    private Context context;
    private Context Mcontext;
    private  AdminBankDeposit adminBankDeposit;
    int position;
    private OnItemsClickListener listener;

    public ADepositAdapter(ArrayList<AdminBankDeposit> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public ADepositAdapter(View itemView, final Callback callback) {
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

    public ADepositAdapter(Context context, int resources, ArrayList<AdminBankDeposit> adminBankDeposits) {
        this.savings = adminBankDeposits;
        this.mcontext = context;
        this.resources = resources;

    }

    public ADepositAdapter(Context context, ArrayList<AdminBankDeposit> deposits) {
        this.savings = deposits;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public ADepositAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_row, parent, false);
        return new ADepositAdapter.RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        AdminBankDeposit adminBankDeposit = this.savings.get(position);
        holder.depositReportID.setText(MessageFormat.format("Deposit ID:{0}", String.valueOf(adminBankDeposit.getDepositID())));
        holder.depositDate.setText(MessageFormat.format("Deposit date:{0}", adminBankDeposit.getDepositDate()));
        holder.depositedAmount.setText(MessageFormat.format("Amount Deposited: NGN{0}", String.format("%.2f", adminBankDeposit.getDepositAmount())));
        holder.status.setText(MessageFormat.format("Status:{0}", adminBankDeposit.getDepositStatus()));
        holder.depositor.setText(MessageFormat.format("Depositor:{0}", adminBankDeposit.getDepositor()));
        holder.officeBranch.setText(MessageFormat.format("Office Branch:{0}",  adminBankDeposit.getDepositOfficeBranch()));
        holder.bankOfSkylight.setText(MessageFormat.format("Bank:{0}", adminBankDeposit.getDepositBank()));


        if (adminBankDeposit.getDepositStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.depositor.setTextColor(Color.MAGENTA);
        } else if (adminBankDeposit.getDepositStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.depositor.setTextColor(Color.RED);
        } else if (adminBankDeposit.getDepositStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.depositor.setTextColor(Color.RED);
        }


    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(adminBankDeposit);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list = adminBankDeposits;
                } else {
                    List<AdminBankDeposit> filteredList = new ArrayList<>();
                    for (AdminBankDeposit adminBankDeposit : adminBankDeposits) {
                        if (adminBankDeposit.getDepositBank().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(adminBankDeposit);
                        }
                    }
                    list = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<AdminBankDeposit>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView depositor, depositDate, status, officeBranch, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView depositedAmount, bankOfSkylight, depositReportID;
        public AppCompatButton btnMore;
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.deposit_icon);
            depositReportID = convertView.findViewById(R.id.depositReportID);
            depositor = convertView.findViewById(R.id.adminManager);
            depositDate = convertView.findViewById(R.id.dReportDate);
            bankOfSkylight = convertView.findViewById(R.id.scFrom);
            depositedAmount = convertView.findViewById(R.id.bankDAmount);
            officeBranch = convertView.findViewById(R.id.adminOffice);
            status = convertView.findViewById(R.id.depositStatus);

            //reportAmountTendered = convertView.findViewById(R.id.bankDAmount);


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


    public AdminBankDeposit getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<AdminBankDeposit> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(AdminBankDeposit adminBankDeposit);
    }

}
