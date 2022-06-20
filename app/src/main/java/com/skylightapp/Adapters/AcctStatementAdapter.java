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

import com.skylightapp.Classes.AcctStatement;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.Classes.ProfileManager;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AcctStatementAdapter extends RecyclerView.Adapter<AcctStatementAdapter.RecyclerViewHolder> implements Filterable {
    private ArrayList<AcctStatement> savings;
    private Context mcontext;
    int resources;
    private List<AcctStatement> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static ProfileManager profileManager;
    private Context context;
    int position;
    private TellerReportAdapterSuper.OnItemsClickListener listener;
    private List<AcctStatement> tellerReportArrayList = new ArrayList<>();
    private List<AcctStatement> itemsListFilter = new ArrayList<>();

    public AcctStatementAdapter(ArrayList<AcctStatement> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public AcctStatementAdapter(View itemView, final Callback callback) {
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

    public AcctStatementAdapter(Context context, int resources, ArrayList<AcctStatement> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;
        this.resources = resources;

    }

    public AcctStatementAdapter(Context context, ArrayList<AcctStatement> tellerReports) {
        this.savings = tellerReports;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acct_statement_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        //SavingsAdapter.bindData(getItemByPosition(position));
        AcctStatement acctStatement = this.savings.get(position);
        holder.settelmentDate.setText(MessageFormat.format("Statement Date:{0}", acctStatement.getDate()));
        holder.soNo.setText(MessageFormat.format("Report date:{0}", acctStatement.getSoNo()));
        holder.walletID.setText(MessageFormat.format("Report Cash Amount: NGN{0}", acctStatement.getWalletAcct_No()));
        holder.soBalance.setText(MessageFormat.format("Total: NGN{0}", String.format("%.2f", acctStatement.getTotalSOs())));
        holder.amountPaidOnline.setText(MessageFormat.format("Amount Paid:NGN{0}", String.valueOf(acctStatement.getTotalBankDeposits())));
        holder.walletBalance.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getTotalSavings())));
        holder.customerName.setText(MessageFormat.format("Teller:{0}", acctStatement.getNameOfCustomer()));
        holder.noOfSavings.setText(MessageFormat.format("Status:{0}", acctStatement.getNoOfSavings()));
        holder.cashCollected.setText(MessageFormat.format("Status:{0}", acctStatement.getTotalCashDeposits()));
        holder.withdrawals.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getTotalWithdrawals())));
        holder.withdrawals.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getTotalWithdrawals())));
        holder.grpSavings.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getGrpSavingsAmt())));
        holder.loans.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getTotalLoans())));
        holder.grpSavingsBalance.setText(MessageFormat.format("Balance: NGN{0}", String.format("%.2f", acctStatement.getGrpSavingsBalance())));



    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tellerReportArrayList = (List<AcctStatement>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AcctStatement> filteredList = new ArrayList<>();
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

                for (AcctStatement dataNames : itemsListFilter) {
                    // filter by title
                    if (dataNames.getDate().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                    if (dataNames.getNameOfCustomer().toLowerCase().trim().contains(searchText)) {
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

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnItemsClickListener{

        public TextView customerName, savingsCount, soNo, noOfSavings, walletBalance, walletID;
        public AppCompatImageView icon;
        private TextView soBalance, amountPaidOnline, tellerReportID, settelmentDate, cashCollected,withdrawals,loans,grpSavingsBalance,grpSavings;
        public AppCompatButton btnMore;
        private Context context;
        private List<AcctStatement> mSliderItems = new ArrayList<>();
        private Context Mcontext;
        private List<AcctStatement> theSlideItemsModelClassList = new ArrayList<>();
        private List<AcctStatement> itemsListFilter = new ArrayList<>();
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.statement_image);
            customerName = convertView.findViewById(R.id.statement_name);
            soNo = convertView.findViewById(R.id.So_No);
            walletID = convertView.findViewById(R.id.wallet_ID);
            walletBalance = convertView.findViewById(R.id.amtStatement);
            soBalance = convertView.findViewById(R.id.SoBalance);
            settelmentDate = convertView.findViewById(R.id.dateStatement);
            noOfSavings = convertView.findViewById(R.id.noOfSavingsStatement);
            cashCollected = convertView.findViewById(R.id.cashStatement);
            amountPaidOnline = convertView.findViewById(R.id.bankDeposit);
            withdrawals = convertView.findViewById(R.id.statementWithdrawals);
            loans = convertView.findViewById(R.id.loansStatement);
            grpSavings = convertView.findViewById(R.id.statementGrpSavings);
            grpSavingsBalance = convertView.findViewById(R.id.grpSavingsBalance);

        }

        @Override
        public void onItemClick(AcctStatement tellerReport) {

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


    public AcctStatement getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<AcctStatement> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(AcctStatement tellerReport);
    }

}
