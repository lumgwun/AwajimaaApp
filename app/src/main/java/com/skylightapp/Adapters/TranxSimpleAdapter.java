package com.skylightapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.User;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;

public class TranxSimpleAdapter extends RecyclerView.Adapter<TranxSimpleAdapter.ViewHolder> implements ListAdapter {

    private Context context;
    private int resource;
    String statusSwitch1;
    private final ArrayList<Transaction> transactions;
    DBHelper applicationDb;
    String status;
    Profile userProfile;
    Customer customer;
    User user;
    SharedPreferences userPreferences;
    private Gson gson;
    private OnItemsClickListener listener;


    public TranxSimpleAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super();
        this.context = context;
        this.resource = resource;
        this.transactions = transactions;
    }

    public TranxSimpleAdapter(Context context, ArrayList<Transaction> transactions2) {
        this.context = context;
        this.transactions = transactions2;
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_simple_row, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return (null != transactions ? transactions.size() : 0);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTransactionID;
        public final TextView txtTransactionTimestamp;
        public final TextView txtTransactionAmount;
        public final TextView payee;
        public final TextView payer;
        public final AppCompatImageView imgTransactionIcon;
        public final TextView type;
        public final CardView cardView;
        public final TextView method;
        public final TextView officeBranch;
        public final TextView status;
        //public final SwitchCompat switchCompat;

        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = view.findViewById(R.id.card_view_transaction44);
            imgTransactionIcon = view.findViewById(R.id.txId44);
            type = view.findViewById(R.id.txt_tranx_type);
            txtTransactionID = view.findViewById(R.id.txt_transaction_ID);
            txtTransactionTimestamp = view.findViewById(R.id.txt_transact_Date);
            txtTransactionAmount = view.findViewById(R.id.tranX_amount);
            method = view.findViewById(R.id.tranX_Method);
            payer = view.findViewById(R.id.txt_Tranx_Payer);
            payee = view.findViewById(R.id.payeeTX);
            officeBranch = view.findViewById(R.id.TRANXOffice);
            status = view.findViewById(R.id.TRANX_status);
            //switchCompat = (SwitchCompat) view.findViewById(R.id.switch_tx_action);

        }


    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = transactions.get(position);
        final Transaction transaction = transactions.get(position);
        final CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        final SkyLightPackage skyLightPackage = new SkyLightPackage();
        final Customer customer = new Customer();
        long customerID = customer.getCusUID();
        final Payment payee = new Payment();
        holder.txtTransactionID.setText(MessageFormat.format("TX ID: {0}{1}", transaction.getTransactionID()));
        holder.txtTransactionAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", transaction.getRecordAmount())));
        holder.officeBranch.setText(MessageFormat.format("Office Branch: {0}", transaction.getTransactionOfficeBranch()));
        holder.txtTransactionTimestamp.setText(MessageFormat.format("Date: {0}", transaction.getDate()));
        holder.method.setText(MessageFormat.format("TX Method: {0}", transaction.getMethodOfPay()));
        holder.payee.setText(MessageFormat.format("Payee: {0}", transaction.getPayee()));
        holder.payer.setText(MessageFormat.format("Payer: {0}-{1}-{2}", transaction.getPayer()));
        holder.status.setText(MessageFormat.format("Status: {0}", customerDailyReport.getDocStatus()));

        holder.txtTransactionID.setText(MessageFormat.format("{0} - {1}", transaction.getTransactionType().toString(), transaction.getTransactionID()));
        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.ic_account_balance_background);
            holder.txtTransactionID.setText(MessageFormat.format("To Payee: {0}", transaction.getPayee()));
            holder.txtTransactionAmount.setTextColor(Color.RED);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.MANUAL_WITHDRAWAL) {
            holder.imgTransactionIcon.setImageResource(R.drawable.transfer3);

            holder.txtTransactionID.setText(MessageFormat.format("From: {0} - To: {1}", transaction.getSendingAccount(), transaction.getDestinationAccount()));
            holder.txtTransactionAmount.setTextColor(Color.DKGRAY);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.deposit);
            //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        }
        /*holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.status.setText("Approved");
                applicationDb.updateTransactionStatus((customerDailyReport.getStatus()),transaction);

            } else {
                holder.status.setText("Not approved");
            }
        });*/


    }
    public interface OnItemsClickListener{
        void onItemClick(Transaction transaction);
    }


}
